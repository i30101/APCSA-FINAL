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

    /* Other trading metrics */
    private int volatility;
    
    private Hashtable<Integer, Double> pastMonth;    // price history for past month, index 0 is most recent
    private Hashtable<Integer, Double> pastYear;     // price history for past year, index 0 is most recent


    /**
     * Default Stock constructor
     * @param pName name of corporation, commodity, ETf, etc.
     * @param pTicker ticker of stock
     * @param pIndustry industry of the stock for classification
     */
    public Stock(String pName, String pTicker, String pIndustry) {
        name = pName;
        ticker = pTicker;
        industry = pIndustry;

        transactionPrice = randNormPrice();
        previousClose = transactionPrice;

        size = "Penny";
        if(transactionPrice > 500) {
            size = "Large";
        }else if(transactionPrice > 20) {
            size = "Medium";
        }
        
        outlook = randNormOutlook();
        volatility = 1;

        pastMonth = new Hashtable<Integer, Double>();
        pastYear = new Hashtable<Integer, Double>();
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
        industry = pIndustry;

        transactionPrice = randNormPrice(mean, stdDev);
        previousClose = transactionPrice;

        size = "Penny";
        if(transactionPrice > 500) {
            size = "Large";
        }else if(transactionPrice > 20) {
            size = "Medium";
        }

        outlook = randNormOutlook();
        volatility = pVolatility;
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
     * @return price
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
     * @return
     */
    private double randNormPrice(double mean, double stdDev) {
        return round(mean + random.nextGaussian() * stdDev);
    }



    /**
     * New transaction
     * Price of stock is changed by a factor of the current stock price
     * Whether the price increases or decreases is determined by the outlook
     * Price change is capped at 0.5% of the current price
     * Updates price history
     */
    public void nextTransaction() {

        // update price history
        if(pastMonth.size() < 30){
            pastMonth.put(pastMonth.size(), transactionPrice);
        } else {
            pastMonth.remove(0);
            pastMonth.put(pastMonth.size(), transactionPrice);
        }
        if(pastYear.size() < 365){
            pastYear.put(pastYear.size(), transactionPrice);
        } else {
            pastYear.remove(0);
            pastYear.put(pastYear.size(), transactionPrice);
        }


        double previous = transactionPrice;
        int factor = -1;
        if(Math.random() < outlook) {
            factor = 1;
        }

        transactionPrice = round(transactionPrice + factor * transactionPrice * (Math.random() * 0.005) * volatility);
        lastTransactionChange = round(transactionPrice - previous);
        dayChange = round(transactionPrice - previousClose);
        dayChangePercent = round(100 * dayChange / previousClose);
        
        outlook += factor * Math.random() * 0.005;
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
     * @return formatted string of metrics
     */
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
    
    
    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getSize() {
        return size;
    }

    public String getIndustry() {
        return industry;
    }

    public double getTransactionPrice() {
        return transactionPrice;
    }

    public double getLastTransactionChange() {
        return lastTransactionChange;
    }

    public double getOutlook() {
        return outlook;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getDayChange() {
        return dayChange;
    }

    public double getDayChangePercent() {
        return dayChangePercent;
    }

    public int getVolatility() {
        return volatility;
    }

    public Hashtable<Integer, Double> getPastMonth() {
        return pastMonth;
    }

    public Hashtable<Integer, Double> getPastYear() {
        return pastYear;
    }
}
