package java;
import java.util.*;

public class Driver {
    public static int runs;

    public static void basicSingleTest() {
        Stock intel = new Stock("Intel", "INTC");
        for(int i = 0; i < runs; i++) {
            System.out.println(intel);
            intel.newTransaction();
        }
        System.out.println(intel);
    }


    public static void basicTrioTest() {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        stocks.add(new Stock("Intel", "INTC"));
        stocks.add(new Stock("Qualcomm", "QCOM"));
        stocks.add(new Stock("Coca-Cola", "KO"));

        System.out.println("TICKER\t|   PRICE\t|   CHANGE \t|   DAY CHANGE\t|   OUTLOOK");
        for(int i = 0; i < runs; i++) {
            for(Stock s: stocks) {
                System.out.println("\n\n");
                s.newTransaction();
                // System.out.println(s);
                System.out.println(s.summary());
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Stock intel = new Stock("Intel Corporation", "INTC");
    }
}
