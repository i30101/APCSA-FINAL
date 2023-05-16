import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class StocksPanel extends JPanel {
    private ArrayList<Stock> stocks;

    public StocksPanel() {
        super();
        stocks = new ArrayList<Stock>();
        reload();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload() {
        GridLayout c = new GridLayout(0,4);
        removeAll();
        stocks.clear();
        setLayout(c);

        for (Stock s : Broker.getStocks()) {

            stocks.add(s); // to allow for manipulation later

            ScaledLabel stockName = new ScaledLabel();
            stockName.setText(s.getTicker() + " - " + s.getName());
            stockName.setFont(Options.getFont());
            add(stockName, c);

            ScaledLabel priceLabel = new ScaledLabel();
            priceLabel.setText("$ " + s.getTransactionPrice());
            priceLabel.setFont(Options.getFont());
            add(priceLabel, c);

            ScaledLabel dayChangeLabel = new ScaledLabel();
            dayChangeLabel.setFont(Options.getFont());
            if (s.getDayChange() > 0) {
                dayChangeLabel.setText(
                        "<html><font color='green'>+" + s.getDayChange() + " (+" + s.getDayChangePercent() + "%)"
                                + "</font></html>");
            } else if (s.getDayChange() < 0) {
                dayChangeLabel
                        .setText("<html><font color='red'>" + s.getDayChange() + " (" + s.getDayChangePercent() + "%)"
                                + "</font></html>");
            } else {
                dayChangeLabel.setText("" + s.getDayChange());
            }
            add(dayChangeLabel, c);

            Button button = new Button("Options");
            button.setFont(Options.getFont());
            button.addActionListener(e -> {
                new StockOptionsWindow(s, button.getX(),button.getY() + button.getHeight());
            });

            add(button, c);

        }

        Button stepTransaction = new Button("Step Transaction");
        stepTransaction.setFont(Options.getFont());
        stepTransaction.addActionListener(e -> {
            Broker.newTransactions();
            reload();
        });
        Button stepTransaction100 = new Button("Step Transaction 100 times");
        stepTransaction100.setFont(Options.getFont());
        stepTransaction100.addActionListener(e -> {
            for (int i = 0; i < 100; i++)
                Broker.newTransactions();
            reload();
        });
        add(stepTransaction, c);
        add(stepTransaction100, c);
        revalidate();
        repaint();
    }
}
