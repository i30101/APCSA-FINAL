
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private GridBagConstraints c;
    private ArrayList<Stock> stocks;
    Font font = new Font("Verdana", Font.PLAIN, 10);
    

    public DisplayPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        stocks = new ArrayList<Stock>();
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
        add(new ScaledLabel("Dashboard"));
        revalidate();
        repaint();
    }

    public void displayStocks() {
        removeAll();
        repaint();

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.5;
        c.weighty = 1;

        c.gridy = 0;


        for (Stock s : Broker.getStocks()) {

            stocks.add(s); // to allow for manipulation later

            c.gridx = 0;
            c.gridy++;

            ScaledLabel stockName = new ScaledLabel();
            stockName.setText(s.getName() + " - " + s.getTicker());
            stockName.setFont(font);
            add(stockName, c);

            c.gridx = 1;

            ScaledLabel priceLabel = new ScaledLabel();
            priceLabel.setText("$ " + s.getTransactionPrice());
            priceLabel.setFont(font);
            add(priceLabel, c);

            c.gridx = 2;

            ScaledLabel dayChangeLabel = new ScaledLabel();
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
                StockOptionsWindow optionWindow = new StockOptionsWindow(s);
                optionWindow.setLocation(button.getX(), button.getY() + button.getHeight());
            });

            add(button, c);


        }

        c.gridx = 0;
        c.gridy = Broker.getStocks().size() + 1;
        c.gridwidth = 4;

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
        c.gridy++;
        add(stepTransaction100, c);

        revalidate();
        repaint();
    }

    public void displayOptions() {
        removeAll();

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;

        ScaledLabel optionsLabel = new ScaledLabel("Options");
        optionsLabel.setFont(font);
        add(optionsLabel, c);

        ScaledLabel fontSizeLabel = new ScaledLabel("Font size: ");
        fontSizeLabel.setFont(font);

        JTextField fontSizeInput = new JTextField(4);
        fontSizeInput.setFont(font);
        fontSizeInput.setText("" + Options.getFont().getSize());

        c.gridy = 1;
        c.gridx = 0;
        add(fontSizeLabel, c);
        c.gridx = 1;
        add(fontSizeInput, c);

        c.gridy = 2;
        c.gridx = 0;
        Button applyButton = new Button("Apply");
        applyButton.setFont(font);
        applyButton.addActionListener(e -> {
            try {
                int fontSize = Integer.parseInt(fontSizeInput.getText());
                if (fontSize > 0 && fontSize < 50) {
                    font = new Font("Verdana", Font.PLAIN, fontSize);
                    Options.setFont(font);
                    displayOptions();
                }else{
                    JOptionPane.showMessageDialog(null, "Please enter a valid font size between 1 and 50");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(applyButton, c);
        revalidate();
        repaint();
    }

    public void displayMatthew() {
        removeAll();
        add(new ScaledLabel("Matthew !!!!"));
        revalidate();
        repaint();
    }
}
