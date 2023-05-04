/* Master class for all stocs and ETFs */

import java.util.*;

public class Stock {
    private static ArrayList<String> stocks;
    private Random random = new Random();
    

    private double previousClose;   // price of stock at end of previous trading day
    private double dayPrice;   // current price of stock in day
    private double outlook; // likelihood of a price increase
    private String name;
    private String ticker;
    private String size;
    private String industry; 
    private Hashtable<String, Double> pastMonth;    // price history for past month
    private Hashtable<String, Double> pastYear;     // price history for past year

    
    public Stock(String n, String t, String i) {
        name = n;
        ticker = t;
        industry = i;
        previousClose = randNormPrice();
        outlook = randNormOutlook();
    }



    /* Price of stock changes
     * 
     */
    public void change() {
        int factor = -1;
        if(Math.random() < outlook) {
            factor = 0;
        }

        dayPrice += factor * dayPrice * (Math.random() * 0.005);
        outlook += factor * Math.random() * 0.005;
    }


    /* Day price of stock changes
     * 
     */
    public void dayChange() {

    }


    private double round(double d) {
        return Math.round(d * 100) / 100;
    }


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


    private double randNormOutlook() {
        double randomOutlook = -1;
        while(randomOutlook < 0) {
            double z = random.nextGaussian();
            randomOutlook = 0.5 + z * 0.1;
        }
        return randomOutlook;
    }

    @Override
    public String toString() {
        return ticker + " | $" + dayPrice;
    }
    
}