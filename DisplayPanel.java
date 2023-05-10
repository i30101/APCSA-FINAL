
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private GridBagConstraints c;
    private Runtime runtime;
    Font font = new Font("Verdana", Font.PLAIN, 10);

    public DisplayPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        runtime = Runtime.getRuntime();
    }

    public void openByID(String str) {
        switch (str) {
            case "dashboard":
                displayDashboard();
                break;
            case "stocks":
                displayStocks();
                break;
            case "options":
                displayOptions();
                break;
            case "matthew":
                displayMatthew();
                break;
        }
    }

    public void displayDashboard() {
        removeAll();
        c.gridy = 0;
        c.gridx = 0;
        add(new JLabel("Dashboard"));
        revalidate();
        repaint();
    }

    public void displayStocks() {

        repaint();
        removeAll();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.anchor = GridBagConstraints.CENTER;
        add(new JLabel("Stocks"), c);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.5;
        c.weighty = 1;

        c.gridy = 0;

        font = new Font("Verdana", Font.PLAIN, getWidth() / 100);

        for (Stock s : Broker.getStocks()) {
            c.gridx = 0;
            c.gridy++;

            JLabel stockName = new JLabel();
            stockName.setText(s.getName() + " - " + s.getTicker());
            stockName.setFont(font);
            add(stockName, c);

            c.gridx = 1;

            JLabel priceLabel = new JLabel();
            priceLabel.setText("$ " + s.getTransactionPrice());
            priceLabel.setFont(font);
            add(priceLabel, c);

            c.gridx = 2;

            JLabel dayChangeLabel = new JLabel();
            dayChangeLabel.setFont(font);
            if (s.getDayChange() > 0) {
                dayChangeLabel.setText("<html><font color='green'>+" + s.getDayChange() + " (+" + s.getDayChangePercent() + "%)"
                        + "</font></html>");
            } else if (s.getDayChange() < 0) {
                dayChangeLabel.setText("<html><font color='red'>" + s.getDayChange() + " (" + s.getDayChangePercent() + "%)"
                        + "</font></html>");
            } else {
                dayChangeLabel.setText("" + s.getDayChange());
            }
            add(dayChangeLabel, c);

            c.gridx = 3;

            Button button = new Button("Options");
            button.setFont(font);
            button.addActionListener(e -> {
                System.out.println(getWidth() + "," + getHeight());
            });

            add(button, c);
        }

        c.gridx = 0;
        c.gridy = Broker.getStocks().size() + 1;

        Button stepTransaction = new Button("Step Transaction");
        stepTransaction.setFont(font);
        stepTransaction.addActionListener(e -> {
            Broker.newTransactions();
            displayStocks();
        });
        Button stepTransaction100 = new Button("Step Transaction 100 times");
        stepTransaction100.setFont(font);
        stepTransaction100.addActionListener(e -> {
            for (int i = 0; i < 100; i++)
                Broker.newTransactions();
            displayStocks();
        });
        add(stepTransaction, c);
        c.gridx++;
        add(stepTransaction100, c);

        revalidate();
        repaint();
    }

    public void displayOptions() {
        removeAll();
        add(new JLabel("Options"));
        revalidate();
        repaint();
    }

    public void displayMatthew() {
        removeAll();
        add(new JLabel("Matthew 💀💀"));
        revalidate();
        repaint();
    }
}
