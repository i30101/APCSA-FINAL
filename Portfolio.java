/**
 * @version 1.0.0 18 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.util.Hashtable;

public class Portfolio {
    // ticker, shares owned
    private static Hashtable<String, Integer> ownedStocks;
    private static Hashtable<String, Double> spentOnStocks;
    private static final String SYSTEM_NAME = System.getProperty("user.name");
    private static double balance;

    public Portfolio() {
        /**
         * Number of shares of each stock
         */
        ownedStocks = new Hashtable<>();
        for(Stock stock : Broker.getStocks()){
            ownedStocks.put(stock.getTicker(), 0);
        }

        /**
         * Amount of money spent on each stock
         * Used to calculate the average amount spent on each share
         */
        spentOnStocks = new Hashtable<>();
        for(Stock stock : Broker.getStocks()) {
            spentOnStocks.put(stock.getTicker(), 0.0);
        }
        balance = 10000;
    }

    /**
     * Buys an amount of stock specified. Returns -1 if there is not enough money.
     * @param ticker
     * @param amount
     * @return cost of buying those stocks
     */
    public static double buyStock(String ticker, int amount){
        double currentStockPrice = Broker.getStock(ticker).getTransactionPrice();
        if(currentStockPrice * amount <= balance){
            if(ownedStocks.get(ticker) == null) {
                ownedStocks.put(ticker, 0);
            }

            // increase number of owned shares
            ownedStocks.put(ticker, ownedStocks.get(ticker) + amount);

            // amount of money spent for transaction
            double moneySpent = currentStockPrice * amount;

            balance -= Stock.round(moneySpent);
            balance = Stock.round(balance);

            // add money spent on each stock
            spentOnStocks.put(ticker, spentOnStocks.get(ticker) + moneySpent);

            return Stock.round(currentStockPrice * amount);
        }
        return -1;
    }

    /**
     * Sells an amount of stock specified. Returns -1 if there is not enough of that stock owned.
     * @param ticker
     * @param amount
     * @return double based on profit!
     */
    public static double sellStock(String ticker, int amount){
        double currentStockPrice = Broker.getStock(ticker).getTransactionPrice();
        if(ownedStocks.get(ticker) >= amount){
            // number of stock owned
            int numSharesOwned = ownedStocks.get(ticker);

            // decrease number of owned shares
            ownedStocks.put(ticker, numSharesOwned - amount);

            // add realized money to balance
            balance += Stock.round(currentStockPrice * amount);
            balance = Stock.round(balance);

            /**
             * Change amount of money spent on stock
             * Multiply amount by fraction of shares sold in transaction
             */
            double fractionSold = amount / numSharesOwned;
            spentOnStocks.put(ticker, Stock.round(spentOnStocks.get(ticker) * fractionSold));

            return Stock.round(currentStockPrice * amount);
        }
        return -1;
    }

    /**
     * Returns the stock amount using ticker.
     * @param ticker
     * @return amount of stock owned. Returns 0 if ticker does not exist in program
     */
    public static int getStockAmount(String ticker){
        if(ownedStocks.get(ticker) != null)
            return ownedStocks.get(ticker);
        return 0;
    }

    public static Hashtable<String, Integer> getOwnedStocks() {
        return ownedStocks;
    }
    
    public static void addMoney(int n){
        balance += n;
        balance = Stock.round(balance);
    }

    public static void removeMoney(int n){
        balance -= n;
        balance = Stock.round(balance);
    }

    public static double getBalance() {
        return Stock.round(balance);
    }

    public static String getSystemName() {
        return SYSTEM_NAME;
    }

    public static double averageSpentOnStock(String ticker) {
        return Stock.round(spentOnStocks.get(ticker) / ownedStocks.get(ticker));
    }

	public static double getTotalNetworth() {
        double networth = balance;
        for(String key : ownedStocks.keySet()){
            networth += Broker.getStock(key).getTransactionPrice() * ownedStocks.get(key);
        }
		return Stock.round(networth);
	}
}
