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
        GridLayout c = new GridLayout(0, 1);
        removeAll();
        setLayout(c);

        int alternate = 1;
        add(new ScaledLabel("Stocks", (int) (Options.getFont().getSize() * 1.5)));
        for (Stock s : Broker.getStocks()) {
            addStockRow(s, alternate);
            alternate = (alternate + 1) % 2;
        }

        revalidate();
        repaint();
    }

    public void addStockRow(Stock s, int alternate) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230 - (20 * alternate), 230 - (20 * alternate), 230 - (20 * alternate)));
        panel.setLayout(new GridLayout(1, 4));
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
            new StockOptionsWindow(s, button.getX(), button.getY() + button.getHeight());
        });

        panel.add(button);
        add(panel);

    }
}
