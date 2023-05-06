public class Driver {
    public static int runs;

    public static void basicSingleTest() {
        Stock intel = new Stock("Intel", "INTC", "Semiconductor");
        for(int i = 0; i < runs; i++) {
            System.out.println(intel);
            intel.nextTransaction();
        }
        System.out.println(intel);
    }


    public static void basicTrioTest() {
        Stocks.clearStocks();

        Stocks.addStock(new Stock("Intel", "INTC", "Semiconductor"));
        Stocks.addStock(new Stock("Qualcomm", "QCOM", "Semiconductor"));
        Stocks.addStock(new Stock("Coca-Cola", "KO", "Consumer Beverage"));

        System.out.println("TICKER\t|   PRICE\t|   CHANGE \t|   DAY CHANGE\t|   OUTLOOK");
        for(int i = 0; i < runs; i++) {
            for(Stock s: Stocks.getStocks()) {
                s.nextTransaction();
                System.out.println(s);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        runs = 100;
        basicTrioTest();
    }
}
