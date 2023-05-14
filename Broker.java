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

        for(int i = 0; i < rawStocks.size(); i++) {
            String[] tempStock = rawStocks.get(i);
            String[] tempPrice = rawPrices.get(i);
            
            double price = Double.parseDouble(tempPrice[tempPrice.length - 1]);

            stocks.add(new Stock(tempStock[0], tempStock[1], tempStock[2], price));
        }

        new GUIDriver();
    }
    

    /**
     * Next transaction for all stocks
     */
    public static void newTransactions() {
        for(Stock s : stocks ) {
            s.newTransaction();
            // System.out.println(s);
        }
        dayWrite();
        monthWrite();
        yearWrite();
    }

    /**
     * Puts lines of CSV into a String array and lines into an arraylist
     * @param filepath file to be read
     * @return contents of CSV file
     */
    private static ArrayList<String[]> readCSV(String filepath) {
        ArrayList<String[]> temp = new ArrayList<String[]>();
        try {
            Scanner scanner = new Scanner(new File(filepath));
            scanner.useDelimiter("\n");
            while(scanner.hasNext()) {
                String next = scanner.next();
                temp.add(next.split(",", -1));
            }
            scanner.close();
        }catch (Exception e) {
            System.out.println("Could not read file: " + e);
        }
        return temp;
    }


    /**
     * Writes list of CSV line strings
     * @param csvPath path of file
     * @param prices formatted list of prices
     */
    public static void writeCSV(String csvPath, ArrayList<String> prices) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvPath);
            writer.flush();
            // String headers = "Ticker,";
            // for(int i = 0; i < 100; i++) {
            //     headers += i + ",";
            // }
            // int length = headers.length();
            // writer.append(headers.substring(0, length - 1) + "\n");
            for(String p : prices) {
                writer.append(p);
            }
            // System.out.println("Writing successful");
        }catch (IOException e) {
            System.out.println("Error writing CSV file: " + e);
        }finally {
            try {
                writer.flush();
                writer.close();
            }catch (IOException e) {
                System.out.println("Error closing writer: " + e);
            }
        }
    }


    /**
     * Write day price history to CSV
     */
    public static void dayWrite() {
        ArrayList<String> prices = new ArrayList<String>();
        for(Stock s : stocks) {
            String price = s.getPriceHistory().dayPricesFormatted();
            System.out.println(price);
            prices.add(price);
        }
        writeCSV("./data/day-history.csv", prices);
    }


    /**
     * Write month price history to CSV
     */
    public static void monthWrite() {
        ArrayList<String> prices = new ArrayList<String>();
        for(Stock s : stocks) {
            prices.add(s.getPriceHistory().monthPricesFormatted());
        }
        writeCSV("./data/month-history.csv", prices);
    }


    /**
     * Write year price history to CSV
     */
    public static void yearWrite() {
        ArrayList<String> prices = new ArrayList<String>();
        for(Stock s : stocks) {
            prices.add(s.getPriceHistory().yearPricesFormatted());
        }
        writeCSV("./data/year-history.csv", prices);
    }


    /**
     * Writes price history data of alls stocks
     */
    public static void writeHistories() {


    }

    public static ArrayList<Stock> getStocks() {
        return stocks;
    }
}
