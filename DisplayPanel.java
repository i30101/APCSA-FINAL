
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.awt.GridBagConstraints;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private GridBagConstraints c;

    public DisplayPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
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
        add(new JLabel("Dashboard"));
        c.anchor = GridBagConstraints.NORTHWEST;

        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 100;

        for (Stock s : Stocks.getStocks()) {
            add(new JLabel(s.getTicker() + ": " + s.getName()), c);
            c.gridy++;
        }

        c.gridx = 1;
        c.gridy = 1;

        for (Stock s : Stocks.getStocks()) {
            JLabel label = new JLabel();
            label.setText("$ " + s.getTransactionPrice());
            add(label, c);
            c.gridy++;
        }

        c.gridx = 2;
        c.gridy = 1;

        for (Stock s : Stocks.getStocks()) {
            JLabel label = new JLabel();
            if (s.getDayChange() > 0) {
                label.setText("<html><font color='green'>+" + s.getDayChange() + " (+" + s.getDayChangePercent() + "%)"
                        + "</font></html>");
            } else if (s.getDayChange() < 0) {
                label.setText("<html><font color='red'>" + s.getDayChange() + " (" + s.getDayChangePercent() + "%)"
                        + "</font></html>");
            } else {
                label.setText("" + s.getDayChange());
            }
            add(label, c);
            c.gridy++;
        }

        c.gridx = 0;
        c.gridy = Stocks.getStocks().size() + 1;

        JButton stepTransaction = new JButton("Step Transaction");
        stepTransaction.addActionListener(e -> {
            Stocks.nextTransationAll();
            displayDashboard();
        });
        JButton stepTransaction100 = new JButton("Step Transaction 100 times");
        stepTransaction100.addActionListener(e -> {
            for (int i = 0; i < 100; i++) {
                Stocks.nextTransationAll();
            }
            displayDashboard();
        });
        add(stepTransaction, c);
        c.gridx++;
        add(stepTransaction100, c);

        revalidate();
        repaint();
    }

    public void displayStocks() {
        removeAll();
        add(new JLabel("Stocks"));
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridy = 1;
        JButton runPY = new JButton("Run Python");
        runPY.addActionListener(e -> {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec(new String[] { "python.exe", "./graph.py" });
            } catch (IOException a) {
                a.printStackTrace();
            }
            displayStocks();
        });
        add(runPY, c);
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
