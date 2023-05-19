
/**
 * @version 1.0.0 9 May 2023
 * @author Andrew Kim and Dylan Nguyen
 * 
 * Driver class for managing stocks and stock prices
 * Not to be confused with the Portfolio class
 */

import java.util.*;
import java.io.*;

public class Broker {
    private static ArrayList<Stock> stocks = new ArrayList<Stock>();

    public static void main(String[] args) {

        stocks = new ArrayList<Stock>();

        ArrayList<String[]> rawStocks = readCSV("./data/companies.csv");
        ArrayList<String[]> rawPrices = readCSV("./data/day-history.csv");

        for (int i = 1; i < rawStocks.size() - 1; i++) {
            String[] tempStock = rawStocks.get(i);
            String[] tempPrice = rawPrices.get(i);

            double price = Double.parseDouble(tempPrice[tempPrice.length - 1]);

            stocks.add(new Stock(tempStock[0], tempStock[1], tempStock[2], price));
        }

        new Portfolio();
        new GUIDriver();
    }

    /**
     * Next transaction for all stocks
     */
    public static void newTransactions() {
        // new transactions for all stocks
        for (Stock s : stocks) {
            s.newTransaction();
        }
        // write trading histories
        dayWrite();
        monthWrite();
        yearWrite();
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

    public static double formatMoney(double number) {
        // https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
        return Double.parseDouble(String.format("%.2f", Math.round(number * 100.0) / 100.0));
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
}
