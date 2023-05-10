import java.util.ArrayList;

public class Stocks {
    private static ArrayList<Stock> stocks = new ArrayList<Stock>();

    public static ArrayList<Stock> getStocks() {
        return stocks;
    }
    
    public static void add(Stock s) {
        stocks.add(s);
    }

    public static void remove(int index) {
        stocks.remove(index);
    }

    public static void nextTransationAll(){
        for(Stock s : Stocks.getStocks()) {
            s.nextTransaction();
        }
    }
}
