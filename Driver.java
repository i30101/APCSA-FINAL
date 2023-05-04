import java.util.*;

public class Driver {
    public static void main(String[] args) {
        ArrayList<Stock> stocks = new ArrayList<Stock>();

        Stock imtel = new Stock("Imtel", "IMTC", "semiconductor");

        System.out.println(imtel);

        imtel.dayChange();

        System.out.println(imtel);

    }
}
