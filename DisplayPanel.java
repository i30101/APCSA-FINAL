
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.*;

public class DisplayPanel extends JPanel {
    private StocksPanel stocksPanel;
    Font font = new Font("Verdana", Font.PLAIN, 10);
    GridBagConstraints c;
    

    public DisplayPanel() {
        super();
        setLayout(new GridBagLayout());
        stocksPanel = new StocksPanel();
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
        add(new ScaledLabel("Dashboard"),c);
        
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

        GridBagConstraints c = new GridBagConstraints();

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
