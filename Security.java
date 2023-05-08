import java.util.*;

public class Security {
    /* Descriptors */
    private String name;
    private String ticker;

    /* Intra-day trading metrics */
    private double transactionPrice;   // current price of transaction
    private double lastTransactionChange;   // change in stock price after previous transaction
    private double outlook; // likelihood of a price increase

    /* Daily trading metrics */
    private double previousClose;   // price of stock at end of previous trading day
    private double dayChange;
    private double dayChangePercent;  // percent change for the day

    /* Other stock metrics */
    private int volatility;
    private double yearLow;
    private double yearHigh;
}