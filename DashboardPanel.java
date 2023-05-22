import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload() {
        removeAll();
        revalidate();
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        JPanel overviewPanel = new JPanel();
        overviewPanel.setLayout(new BoxLayout(overviewPanel, BoxLayout.Y_AXIS));
        overviewPanel.add(new ScaledLabel("Overview", (int) (Options.getFont().getSize()*1.5)));
        overviewPanel.add(new ScaledLabel("Hello " + Portfolio.getSystemName()));
        overviewPanel.add(new ScaledLabel("Your balance is: $ " + Portfolio.getBalance()));
        overviewPanel.add(new ScaledLabel("Your net worth is: $ " + Portfolio.getTotalNetworth()));
        overviewPanel.add(new ScaledLabel("Your total profit is: $ " + (10000 - Portfolio.getBalance())));
        overviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        c.gridy = 0;
        add(overviewPanel,c);

        JPanel stocksOverviewPanel = new JPanel();
        BoxLayout stocksBoxLayout = new BoxLayout(stocksOverviewPanel, BoxLayout.Y_AXIS);
        
        stocksOverviewPanel.setLayout(stocksBoxLayout);
        stocksOverviewPanel.add(new ScaledLabel("Assets", (int) (Options.getFont().getSize()*1.5)));
        for (String ticker : Portfolio.getOwnedStocks().keySet()) {
            if (Portfolio.getOwnedStocks().get(ticker) == 0)
                continue;
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(0,4));
            row.add(new ScaledLabel(ticker));
            row.add(new ScaledLabel("Owned: " + Portfolio.getOwnedStocks().get(ticker)));
            double totalValue = Portfolio.getOwnedStocks().get(ticker) * Broker.getStock(ticker).getTransactionPrice();
            row.add(new ScaledLabel("Value: $ " + Broker.formatBalance(totalValue)));
            row.add(new ScaledLabel(Broker.getStock(ticker).getDayChangeFormatted()));
            stocksOverviewPanel.add(row);
        }
        if (stocksOverviewPanel.getComponentCount() == 1) 
            stocksOverviewPanel.add(new ScaledLabel("No stocks to display."));
        stocksOverviewPanel.setBorder(border);
        c.gridy = 1;
        add(stocksOverviewPanel,c);
    }
}
