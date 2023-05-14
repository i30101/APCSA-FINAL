/**
 * @version 1.0.0 9 May 2023
 * @author Andrew Kim and Dylan Nguyen
 * 
 * Price history manager for all stocks and indeces
 */

import java.util.*;

public class PriceHistory {
    private static int dayTransactions;
    private String ticker;

    private ArrayList<Double> dayHistory;
    private ArrayList<Double> monthHistory;
    private ArrayList<Double> yearHistory;


    /**
     * Default PriceHistory constructor
     * @param pTicker to identify history
     */
    public PriceHistory(String pTicker) {
        dayTransactions = 0;
        ticker = pTicker;

        dayHistory = new ArrayList<Double>();
        monthHistory = new ArrayList<Double>();
        yearHistory = new ArrayList<Double>();
    }

    
    /**
     * Adds price to intra-day trading history
     * @param intraDay the intra-day price to be added
     */
    public void addIntraDay(double intraDay) {
        dayHistory.add(intraDay);
        dayTransactions++;
    }

    
    /**
     * Adds closing price to history of all days
     * @param previousClose closing price
     */
    public void addDay(double previousClose) {
        if(ticker.equals("DIS")) {
            System.out.println("Mickey Mouse Bad");
        }
        // System.out.println(monthHistory.size());
        monthHistory.add(previousClose);
        if(monthHistory.size() > 30) {
            monthHistory.remove(0);
        }
        yearHistory.add(previousClose);
        if(yearHistory.size() > 360) {
            yearHistory.remove(0);
        }
    }


    /**
     * Resets day trading history
     * @param previousClose the closing price of the previous day, or the starting price
     */
    public void newDay(double previousClose) {
        dayHistory = new ArrayList<Double>();
        dayHistory.add(previousClose);
    }

    
    /**
     * Prepares day trading prices for CSV writing
     * @return formatted string for CSV
     */
    public String dayPricesFormatted() {
        String formatted = ticker + ",";
        for(Double s : dayHistory) {
            formatted += s + ",";
        }
        System.out.println(formatted);
        return formatted.substring(0, formatted.length() - 1) + "\n";
    }


    /**
     * Prepares month prices for CSV writing
     * @return formatted string for CSV
     */
    public String monthPricesFormatted() {
        String formatted = ticker + ",";
        for(Double s : monthHistory) {
            formatted += s + ",";
        }
        return formatted.substring(0, formatted.length() - 1) + "\n";
    }


    /**
     * PRepares year prices for CSV writing 
     * @return formatted string for CSV
     */
    public String yearPricesFormatted() {
        String formatted = ticker + ",";
        for(Double s : yearHistory) {
            formatted += s + ",";
        }
        return formatted.substring(0, formatted.length() - 1) + "\n";
    }


    @Override
    public String toString() {
        return "";
    }

    public static int getDayTransactions() {
        return dayTransactions;
    }
    
    public ArrayList<Double> getDayHistory() {
        return dayHistory;
    }


    public ArrayList<Double> getMonthHistory() {
        return monthHistory;
    }


    public ArrayList<Double> getYearHistory() {
        return yearHistory;
    } 
}
