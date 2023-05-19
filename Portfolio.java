/**
 * @version 1.0.0 18 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.util.Hashtable;

public class Portfolio {
    // ticker, shares owned
    private static Hashtable<String, Integer> ownedStocks;
    private static final String SYSTEM_NAME = System.getProperty("user.name");
    private static double balance;

    public Portfolio() {
        ownedStocks = new Hashtable<>();
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
            if(ownedStocks.get(ticker) == null)
                ownedStocks.put(ticker, 0);
            ownedStocks.put(ticker, ownedStocks.get(ticker) + amount);
            balance -= Broker.formatMoney(currentStockPrice * amount);
            return Broker.formatMoney(currentStockPrice * amount);
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
            ownedStocks.put(ticker, ownedStocks.get(ticker) - amount);
            balance += Broker.formatMoney(currentStockPrice * amount);
            return Broker.formatMoney(currentStockPrice * amount);
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
    }

    public static void removeMoney(int n){
        balance -= n;
    }

    public static double getBalance() {
        return balance;
    }

    public static String getSystemName() {
        return SYSTEM_NAME;
    }

	public static double totalNetworth() {
        double networth = balance;
        for(String key : ownedStocks.keySet()){
            networth += Broker.getStock(key).getTransactionPrice() * ownedStocks.get(key);
        }
		return networth;
	}
}
