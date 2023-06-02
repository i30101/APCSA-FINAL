/**
 * @version 1.0.0, 8 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private String currentPanel;
    private StocksPanel stocksPanel;
    private DashboardPanel dashboardPanel;
    private OptionsPanel optionsPanel;
    Font font = new Font("Verdana", Font.PLAIN, 10);
    GridBagConstraints c;
    

    public DisplayPanel() {
        super();
        setLayout(new GridBagLayout());
        currentPanel = "dashboard";
        stocksPanel = new StocksPanel();
        dashboardPanel = new DashboardPanel();
        optionsPanel = new OptionsPanel();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1;
        c.weighty = 1;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void openByID(String str) {
        currentPanel = str;
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

    public void reloadOpenPanel(){
        openByID(currentPanel);
    }

    public String getCurrentPanel(){
        return currentPanel;
    }
}
