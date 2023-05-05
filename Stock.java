/**
 * @author Andrew Kim and Dylan Ngyuen
 * @version 1.0, 4 May 2023
 * 
 */

import java.util.*;

public class Stock {
    private static Random random = new Random();

    /* Descriptors */
    private String name;
    private String ticker;
    private String size;
    private String industry; 
    
    /* Intra-day trading metrics */
    private double transactionPrice;   // current price of transaction
    private double lastTransactionChange;   // change in stock price after previous transaction
    private double outlook; // likelihood of a price increase


    /* Daily trading metrics */
    private double previousClose;   // price of stock at end of previous trading day
    private double dayChange;
    private double dayChangePercent;  // percent change for the day

    
    private Hashtable<String, Double> pastMonth;    // price history for past month
    private Hashtable<String, Double> pastYear;     // price history for past year

    /**
     * Default Stock constructor
     * @param n name of corporation, commodity, ETf, etc.
     * @param t ticker of stock
     * @param i industry of the stock for classification
     */
    public Stock(String n, String t, String i) {
        name = n;
        ticker = t;
        industry = i;

        transactionPrice = randNormPrice();
        previousClose = transactionPrice;
        
        outlook = randNormOutlook();
    }



    /* New transaction
     * Price of stock is changed by a factor of the current stock price
     * Whether the price increases or decreases is determined by the outlook
     */
    public void nextTransaction() {
        double previous = transactionPrice;
        int factor = -1;
        if(Math.random() < outlook) {
            factor = 1;
        }

        transactionPrice = round(transactionPrice + factor * transactionPrice * (Math.random() * 0.005));
        lastTransactionChange = round(transactionPrice - previous);
        dayChange = round(transactionPrice - previousClose);
        dayChangePercent = round(100 * dayChange / previousClose);
        
        outlook += factor * Math.random() * 0.005;
    }



    /**
     * Rounds values to dollars and cents
     * @param d unshorted double
     * @return double with two decimal places
     */
    private double round(double d) {
        return Math.round(d * 100.0) / 100.0;
    }


    /**
     * Sets stock price and size
     * Values are chosen from a normal distribution
     * @return price
     */
    private double randNormPrice() {
        double average = 10;
        double stdDev = 2;
        size = "Penny";
        if(Math.random() > 0.5) {
            average = 50;
            stdDev = 10;
            size = "Large";
        }else if(Math.random() > 0.5) {
            average = 750;
            stdDev = 500;
            size = "Medium";
        }

        double randomPrice = -1;
        while(randomPrice < 0) {
            double z = random.nextGaussian();
            randomPrice = average + z * stdDev;
        }

        return round(randomPrice);
    }


    /**
     * Sets the probability of stock price increasing
     * Outlook chosen from normal distribution with mean = 0.5, standard deviation = 0.01
     * @return double outlook
     */
    private double randNormOutlook() {
        double randomOutlook = -1;
        while(randomOutlook < 0) {
            double z = random.nextGaussian();
            randomOutlook = 0.5 + z * 0.01;
        }
        return randomOutlook;
    }
    

    public String summary() {
        return name + " Inc. [" + ticker + "]" + 
            "\nPrice: " + transactionPrice + 
            "\nPrice at previous close: $" + previousClose + 
            "\nDay change: " + dayChange + " | " + dayChangePercent + "%" + 
            "\nIndustry: " + industry + 
            "\nSize: " + size;
    }


    /**
     * Print stock summary for day transactions
     * @return fomratted string containing last transaction and daily change
     */
    @Override
    public String toString() {
        String tString = transactionPrice + "";
        int length = tString.length();
        if(tString.substring(length - 2, length - 1).equals(".")) {
            tString += "0";
        }
        String s = ticker + "\t|   $" + tString + "\t|   " + lastTransactionChange + " \t|   " + dayChangePercent + "%";
        s += " \t|   " + outlook;
        return s;
    }
    
}