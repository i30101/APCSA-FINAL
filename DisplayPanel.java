
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Button;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private StocksPanel stocksPanel;
    private DashboardPanel dashboardPanel;
    private OptionsPanel optionsPanel;
    Font font = new Font("Verdana", Font.PLAIN, 10);
    GridBagConstraints c;
    

    public DisplayPanel() {
        super();
        setLayout(new GridBagLayout());
        stocksPanel = new StocksPanel();
        dashboardPanel = new DashboardPanel();
        optionsPanel = new OptionsPanel();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
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
        repaint();
        dashboardPanel.reload();
        add(dashboardPanel,c);
        revalidate();
        repaint();
    }

    public void displayStocks() {
        removeAll();
        repaint();
        stocksPanel.reload();
        add(stocksPanel,c);
        revalidate();
        repaint();
    }

    public void displayOptions() {
        removeAll();
        repaint();
        optionsPanel.reload();
        add(optionsPanel,c);
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
