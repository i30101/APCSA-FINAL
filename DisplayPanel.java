
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    public DisplayPanel() {
        super();
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
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
        revalidate();
        repaint();
    }

    public void displayStocks() {
        removeAll();
        add(new JLabel("Stocks"));
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
        add(new JLabel("Matthew 🤓"));
        revalidate();
        repaint();
    }
}
