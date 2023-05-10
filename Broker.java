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
    private ArrayList<Stock> stocks;

    
    /**
     * Construct default broker and add stocks to market
     */
    public Broker() {
        stocks = new ArrayList<Stock>();
        for(String[] s : readCSV("./data/companies.csv")) {
            stocks.add(new Stock(s[0], s[1], s[2]));
        }
    }


    /**
     * Puts lines of CSV into a String array and lines into an arraylist
     * @param filepath file to be read
     * @return contents of CSV file
     */
    private ArrayList<String[]> readCSV(String filepath) {
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


    public void newTransactions() {
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
    public ArrayList<Double> tickerDayHistory(String ticker) {
        for(Stock s : stocks) {
            if(s.getTicker().equals(ticker)) {
                return s.getPriceHistory().getDayHistory();
            }
        }
        return null;
    }


    public void writeHistories() {
        String csvPath = "./data/day-history.csv";
        FileWriter writer = null;

        try {
            writer = new FileWriter(csvPath);
            writer.flush();
            for(Stock s : stocks) {
                writer.append(s.getPriceHistory().dayPricesFormatted());
                System.out.println("Writing successful");
            }
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


}
