import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class StocksPanel extends JPanel {

    public StocksPanel() {
        super();
        reload();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload() {
        GridLayout c = new GridLayout(0,1);
        removeAll();
        setLayout(c);

        int alternate = 0;
        for (Stock s : Broker.getStocks()) {
            alternate = (alternate + 1) % 2;
            JPanel panel = new JPanel();
            panel.setBackground(new Color(230-(20*alternate),230-(20*alternate),230-(20*alternate)));
            panel.setLayout(new GridLayout(1,4));
            ScaledLabel stockName = new ScaledLabel();
            stockName.setText(s.getTicker() + " - " + s.getName());
            stockName.setFont(Options.getFont());
            panel.add(stockName);

            ScaledLabel priceLabel = new ScaledLabel();
            priceLabel.setText("$ " + s.getTransactionPrice());
            priceLabel.setFont(Options.getFont());
            panel.add(priceLabel);

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
            panel.add(dayChangeLabel);

            Button button = new Button("Options");
            button.setFont(Options.getFont());
            button.addActionListener(e -> {
                new StockOptionsWindow(s, button.getX(),button.getY() + button.getHeight());
            });

            panel.add(button);
            add(panel);

        }

        Button stepTransaction = new Button("Step Transaction");
        stepTransaction.setFont(Options.getFont());
        
        Button stepTransaction100 = new Button("Step Transaction 100 times");
        stepTransaction100.setFont(Options.getFont());
        
        add(stepTransaction, c);
        add(stepTransaction100, c);
        
        stepTransaction.addActionListener(e -> {
            stepTransaction.setLabel("Loading...");
            Broker.newTransactions();
            reload();
        });
        stepTransaction100.addActionListener(e -> {
            stepTransaction100.setLabel("Loading...");
            for (int i = 0; i < 100; i++)
                Broker.newTransactions();
            reload();
        });
        
        revalidate();
        repaint();
    }
}
