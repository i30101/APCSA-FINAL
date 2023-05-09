/**
 * @version 1.0, 9 May 2023
 * @author Andrew Kim and Dylan Nguyen 
 * 
 * Default parent class for Shares, Commodities, and Cryptocurrencies
 */

import java.util.*;

public class Stock {
    private static Random random = new Random();

    /* Descriptors */
    private String name;
    private String ticker;
    
    /* Intra-day trading metrics */
    private double transactionPrice;   // current price of transaction
    private double lastTransactionChange;   // change in stock price after previous transaction
    
    /* Daily trading metrics */
    private double previousClose;   // price of stock at end of previous trading day
    private double dayChange;
    private double dayChangePercent;  // percent change for the day
    
    /* Performance  */
    private double outlook; // likelihood of a price increase

    /* Other stock metrics */
    private int volatility;
    private double yearLow;
    private double yearHigh;
    
    /* Price history */
    private PriceHistory priceHistory;


    /**
     * Default Stock constructor (not to be used)
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     */
    public Stock(String pName, String pTicker) {
        name = pName;
        ticker = pTicker;

        transactionPrice = randNormPrice();
        lastTransactionChange = 0;
        outlook = randNormOutlook();

        previousClose = transactionPrice;
        dayChange = 0;
        dayChangePercent = 0;
        
        volatility = 1;
        yearLow = transactionPrice;
        yearHigh = transactionPrice;

        priceHistory = new PriceHistory();

        priceHistory.addIntraDay(transactionPrice);
        priceHistory.addDay(previousClose);
    }


    /**
     * Custom constructor for child classes
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     * @param pIndustry industry of the stock for classification
     * @param mean center of normal distribution for price RNG
     * @param stdDev spread of normal distribution for price RNG
     * @param pVolatility custom volatility factor
     */
    public Stock(String pName, String pTicker, String pIndustry, double mean, double stdDev, int pVolatility) {
        name = pName;
        ticker = pTicker;

        transactionPrice = randNormPrice(mean, stdDev);
        lastTransactionChange = 0;
        outlook = randNormOutlook();

        previousClose = transactionPrice;
        dayChange = 0;
        dayChangePercent = 0;

        volatility = pVolatility;
        yearLow = transactionPrice;
        yearHigh = transactionPrice;

        priceHistory = new PriceHistory();

        priceHistory.addIntraDay(transactionPrice);
        priceHistory.addDay(previousClose);
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
     * Sets stock size and chooses price from a normal distribution
     * @return price of stock
     */
    private double randNormPrice() {
        double average = 10;
        double stdDev = 2;
        if(Math.random() > 0.5) {
            average = 50;
            stdDev = 10;
        }else if(Math.random() > 0.5) {
            average = 500;
            stdDev = 200;
        }

        double randomPrice = -1;
        while(randomPrice < 0) {
            double z = random.nextGaussian();
            randomPrice = average + z * stdDev;
        }

        return round(randomPrice);
    }

    
    /**
     * Sets price based on normal distribution with given parameters
     * @param mean center of normal distribution
     * @param stdDev standard deviation of normal distribution
     * @return custom random price
     */
    private double randNormPrice(double mean, double stdDev) {
        return round(mean + random.nextGaussian() * stdDev);
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


    /**
     * New transaction
     * Price of stock is changed by a factor of the current stock price
     * Whether the price increases or decreases is determined by the outlook
     */
    public void newTransaction() {
        double previous = transactionPrice;
        int factor = -1;
        if(Math.random() < outlook) {
            factor = 1;
        }

        // change price of stock
        transactionPrice = round(transactionPrice + factor * transactionPrice * (Math.random() * 0.005) * volatility);
        
        // change historical metrics
        if(transactionPrice > yearHigh) {
            yearHigh = transactionPrice;
        }else if(transactionPrice < yearLow) {
            yearLow = transactionPrice;
        }

        // change intra-day metrics
        lastTransactionChange = round(transactionPrice - previous);
        dayChange = round(transactionPrice - previousClose);
        dayChangePercent = round(100 * dayChange / previousClose);
        
        // change outlook
        outlook += factor * Math.random() * 0.005;

        // update day history data;
        priceHistory.addIntraDay(transactionPrice);

    }

    
    /**
     * Record day's trading metrics and reset for a new day
     */
    public void newDay() {
        /* Record day's trading metrics */
        previousClose = transactionPrice;
        priceHistory.addDay(previousClose);

        /* Reset day trading metrics */
        priceHistory.newDay(previousClose);
    }


    /**
     * @return formatted string of metrics
     */
    public String summary() {
        return name + " Inc. [" + ticker + "]" + 
            "\nPrice: " + transactionPrice + 
            "\nPrice at previous close: $" + previousClose + 
            "\nDay change: " + dayChange + " | " + dayChangePercent + "%";
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
