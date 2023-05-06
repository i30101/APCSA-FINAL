import java.util.ArrayList;

public class Stocks {
	private static ArrayList<Stock> stocks = new ArrayList<Stock>();

	public Stocks() {
		stocks = new ArrayList<Stock>();
	}

	/**
	 * Adds a stock to the list of stocks
	 * Will return false if stock already exists
	 * @param Stock s
	 * @return boolean true if stock was added, false if not
	 */
	public static boolean addStock(Stock s) {
		for(Stock stock : stocks)
			if(stock.getTicker().equals(s.getTicker()))
				return false;
		
		stocks.add(s);
		return true;
	}

	/**
	 * Removes a stock from the list of stocks
	 * Will return false if stock does not exist
	 * @param Stock s
	 * @return boolean true if stock was removed, false if not
	 */
	public static boolean removeStock(Stock s) {
		for(Stock stock : stocks)
			if(stock.getTicker().equals(s.getTicker()))
				return false;
		stocks.remove(s);
		return true;
	}

	/**
	 * Returns the list of stocks
	 * @return ArrayList<Stock> stocks
	 */
	public static ArrayList<Stock> getStocks() {
		return stocks;
	}

	/**
	 * Clears the list of stocks
	 */
	public static void clearStocks() {
		stocks.clear();
	}

	/**
	 * Returns a string of all the stocks
	 * @return String s
	 */
	public static String getStocksString() {
		String s = "";
		for(Stock stock : stocks) {
			s += stock.toString() + "\n";
		}
		return s;
	}
}
