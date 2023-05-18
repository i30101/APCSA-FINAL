/**
 * @version 1.0.0 9 May 2023
 * @author Andrew Kim and Dylan Nguyen
 * 
 * Price history manager for all stocks and indeces
 */

import java.util.*;

public class PriceHistory {
    private ArrayList<Double> dayHistory;
    private ArrayList<Double> monthHistory;
    private ArrayList<Double> yearHistory;


    /**
     * Default PriceHistory constructor
     * @param pTicker to identify history
     */
    public PriceHistory(String pTicker) {
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
    }


    /**
     * Resets day trading history
     * @param previousClose the closing price of the previous day, or the starting price
     */
    public void newDay(double previousClose) {
        dayHistory = new ArrayList<Double>();
        dayHistory.add(previousClose);

        monthHistory.add(previousClose);
        if(monthHistory.size() > 30) {
            monthHistory.remove(0);
        }

        yearHistory.add(previousClose);
        if(yearHistory.size() > 360) {
            yearHistory.remove(0);
        }
    }


    @Override
    public String toString() {
        return "";
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
