import java.util.*;

public class PriceHistory {
    private ArrayList<Double> dayHistory;
    private ArrayList<Double> monthHistory;
    private ArrayList<Double> yearHistory;


    /**
     * Default pricehistory constructor
     */
    public PriceHistory() {
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
     * Adds closing price to history of all days
     * @param previousClose closing price
     */
    public void addDay(double previousClose) {
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
        String formatted = "";
        for(Double s : dayHistory) {
            formatted += s + ", ";
        }
        return formatted.substring(0, formatted.length() - 2) + "\n";
    }


    /**
     * Prepares month prices for CSV writing
     * @return formatted string for CSV
     */
    public String monthPricesFormatted() {
        String formatted = "";
        for(Double s : monthHistory) {
            formatted += s + ", ";
        }
        return formatted.substring(0, formatted.length() - 2) + "\n";
    }


    /**
     * PRepares year prices for CSV writing 
     * @return formatted string for CSV
     */
    public String yearPricesFormatted() {
        String formatted = "";
        for(Double s : yearHistory) {
            formatted += s + ", ";
        }
        return formatted.substring(0, formatted.length() - 2) + "\n";
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
