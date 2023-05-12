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
        for(String[] s : readCSV("./data/companies.csv")) {
            stocks.add(new Stock(s[0], s[1], s[2]));
        }
        new GUIDriver();
    }
    
    /**
     * Construct default broker and add stocks to market
     */
    public Broker() {
        
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
                temp.add(next.split(",", 5));
            }
            scanner.close();
        }catch (Exception e) {
            System.out.println("Could not read file: " + e);
        }
        return temp;
    }


    /**
     * Next transaction for all stocks
     */
    public static void newTransactions() {
        for(Stock s : stocks ) {
            s.newTransaction();
            // System.out.println(s);
        }
    }


    /**
     * Obtains day price history of stock
     * @param ticker the desired stock
     * @return the price history
     */
    public static ArrayList<Double> tickerDayHistory(String ticker) {
        for(Stock s : stocks) {
            if(s.getTicker().equals(ticker)) {
                return s.getPriceHistory().getDayHistory();
            }
        }
        return null;
    }


    public static void writeCSV(String csvPath, ArrayList<String> prices) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvPath);
            writer.flush();
            String headers = "Ticker,";
            for(int i = 0; i < 100; i++) {
                headers += i + ",";
            }
            int length = headers.length();
            writer.append(headers.substring(0, length - 1) + "\n");
            for(String p : prices) {
                writer.append(p);
            }
            System.out.println("Writing successful");
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
            prices.add(s.getPriceHistory().dayPricesFormatted());
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
        writeCSV("./data/month-history", prices);
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
