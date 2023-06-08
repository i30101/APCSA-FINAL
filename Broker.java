
/**
 * @version 1.0.0 9, May 2023
 * @author Andrew Kim and Dylan Nguyen
 * 
 * Driver class for managing stocks and stock prices
 * Not to be confused with the Portfolio class
 */

import java.util.*;
import java.io.*;

public class Broker {
    private static int dayTransactions;
    private static ArrayList<Stock> stocks = new ArrayList<Stock>();
    private static boolean safeToClose = true;

    public static void main(String[] args) {
        dayTransactions = 0;

        Options.loadOptions();

        stocks = new ArrayList<Stock>();

        new Portfolio();

        ArrayList<String[]> rawStocks = readCSV("./data/companies.csv");
        ArrayList<String[]> rawPrices = readCSV("./data/day-history.csv");

        for (int i = 1; i < rawStocks.size() - 1; i++) {
            String[] tempStock = rawStocks.get(i);
            String[] tempPrice = rawPrices.get(i);

            double price = Double.parseDouble(tempPrice[tempPrice.length - 1]);

            stocks.add(new Stock(tempStock[0], tempStock[1], tempStock[2], price));
            Portfolio.buyStock(tempStock[0], Options.getStockCount(tempStock[0]));
        }

        new GUIDriver();

        while (true) {
            try {
                Thread.sleep(Options.getSimulationSpeedScaled());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            newTransactions();
            if (Broker.getStock("PUGH").getTransactionPrice() < 1000) {
                Broker.getStock("PUGH").setTransactionPrice(1000);
                Broker.getStock("PUGH").setOutlook(1.0);
            }
        }
    }

    /**
     * Next transaction for all stocks
     */
    public static void newTransactions() {
        // new transactions for all stocks
        dayTransactions++;
        for (Stock s : stocks) {
            s.newTransaction();

            // autosave
            if (dayTransactions % 10 == 0) {
                Options.saveOptions();
            }
            if (dayTransactions % 30 == 0) {
                s.newDay();
                dayTransactions = 0;
            }
        }

        safeToClose = false;
        // write trading histories
        dayWrite();
        monthWrite();
        yearWrite();
        safeToClose = true;
    }

    /**
     * Puts lines of CSV into a String array and lines into an arraylist
     * 
     * @param filepath file to be read
     * @return contents of CSV file
     */
    private static ArrayList<String[]> readCSV(String filepath) {
        ArrayList<String[]> temp = new ArrayList<String[]>();
        try {
            Scanner scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                String next = scanner.next();
                temp.add(next.split(",", -1));
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Could not read file: " + e);
        }
        return temp;
    }

    /**
     * General stock history writer
     * 
     * @param csvPath        path of file
     * @param prices         formatted list of prices
     * @param numberOfPrices the number of prices in history of each stock
     */
    public static void writeCSV(String csvPath, ArrayList<String> prices, int numberOfPrices) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvPath);

            // truncate CSV file before writing
            writer.flush();

            // write header of CSV file
            String headers = "Ticker,";

            for (int i = 0; i < numberOfPrices; i++) {
                headers += i + ",";
            }
            int length = headers.length();
            writer.append(headers.substring(0, length - 1) + "\n");

            // write prices of each stock
            for (String p : prices) {
                writer.append(p);
            }
        } catch (IOException e) {
            System.out.println("Error writing CSV file: " + e);
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                System.out.println("Error closing writer: " + e);
            }
        }
    }

    /**
     * General formatter for Double ArrayLists of prices
     * 
     * @param prices
     * @return
     */
    public static String formatPriceHistory(ArrayList<Double> prices) {
        String formatted = ",";
        for (Double s : prices) {
            formatted += s + ",";
        }
        return formatted.substring(0, formatted.length() - 1) + "\n";
    }

    /**
     * Write day price history to CSV
     */
    public static void dayWrite() {
        // number of trades in day
        int numberOfDayPrices = stocks.get(0).getPriceHistory().getDayHistory().size();

        // put formatted strings into ArrayList
        ArrayList<String> prices = new ArrayList<String>();
        for (Stock s : stocks) {
            String stockDayHistory = s.getTicker() + formatPriceHistory(s.getPriceHistory().getDayHistory());
            prices.add(stockDayHistory);
        }

        writeCSV("./data/day-history.csv", prices, numberOfDayPrices);
    }

    /**
     * Write month price history to CSV
     */
    public static void monthWrite() {
        // number of trades in past month
        int numberOfMonthPrices = stocks.get(0).getPriceHistory().getMonthHistory().size();

        // put formatted strings into ArrayList
        ArrayList<String> prices = new ArrayList<String>();
        for (Stock s : stocks) {
            String stockMonthHistory = s.getTicker() + formatPriceHistory(s.getPriceHistory().getMonthHistory());
            prices.add(stockMonthHistory);
        }
        writeCSV("./data/month-history.csv", prices, numberOfMonthPrices);
    }

    /**
     * Write year price history to CSV
     */
    public static void yearWrite() {
        // number of trades in past year
        int numberOfYearPrices = stocks.get(0).getPriceHistory().getYearHistory().size();

        // put formatted strings into ArrayList
        ArrayList<String> prices = new ArrayList<String>();
        for (Stock s : stocks) {
            String stockYearHistory = s.getTicker() + formatPriceHistory(s.getPriceHistory().getYearHistory());
            prices.add(stockYearHistory);
        }
        writeCSV("./data/year-history.csv", prices, numberOfYearPrices);
    }

    // replaced with Stocks.round()
    public static double formatBalance(double number) {
        // https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
        return Math.round(number * 100.0) / 100.d;
    }

    public static ArrayList<Stock> getStocks() {
        return stocks;
    }

    public static Stock getStock(String str) {
        for (Stock s : stocks) {
            if (s.getTicker().equals(str)) {
                return s;
            }
            if (s.getName().equals(str)) {
                return s;
            }
        }
        return null;
    }

    public static int getDayTransactions() {
        return dayTransactions;
    }

    public static String getDate() {
        return ((PriceHistory.getDays() / 12) + 1) + "/" + (PriceHistory.getDays() % 30) + "/"
                + ((PriceHistory.getDays() / 365) + 1929);
    }

    public static boolean isSafeToClose() {
        return safeToClose;
    }
}
