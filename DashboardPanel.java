/**
 * @version 1.0.0, 8 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

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

        GridBagConstraints overviewConstraints = new GridBagConstraints();
        overviewConstraints.fill = GridBagConstraints.BOTH;
        overviewConstraints.weightx = 1;
        overviewConstraints.weighty = 0.1;
        overviewConstraints.gridx = 0;
        overviewConstraints.gridy = 0;

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        JPanel overviewPanel = new JPanel();
        overviewPanel.setLayout(new BoxLayout(overviewPanel, BoxLayout.Y_AXIS));
        overviewPanel.add(new ScaledLabel("Overview", (int) (Options.getFont().getSize() * 1.5)));
        overviewPanel.add(new ScaledLabel("Hello " + Portfolio.getSystemName()));
        overviewPanel.add(new ScaledLabel("Your balance is: $ " + Portfolio.getBalance()));
        overviewPanel.add(new ScaledLabel("Your net worth is: $ " + Portfolio.getTotalNetworth()));
        overviewPanel
                .add(new ScaledLabel("Your total profit is: $ " + Stock.round(Portfolio.getTotalNetworth() - 10000)));
        overviewPanel.add(new ScaledLabel(
                "Intra-day update count: " + Broker.getStock("AAPL").getPriceHistory().getDayHistory().size() + "/30"));
        overviewPanel.add(new ScaledLabel("Date: " + Broker.getDate()));
        overviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        overviewConstraints.gridy = 0;
        add(overviewPanel, overviewConstraints);

        JPanel stocksOverviewPanel = new JPanel();
        GridBagConstraints stocksConstraints = new GridBagConstraints();
        stocksConstraints.fill = GridBagConstraints.BOTH;
        stocksConstraints.weightx = 1;
        stocksConstraints.weighty = 1;
        stocksConstraints.gridx = 0;
        stocksConstraints.gridy = 0;

        stocksOverviewPanel.setLayout(new GridBagLayout());
        stocksOverviewPanel.add(new ScaledLabel("Assets", (int) (Options.getFont().getSize() * 1.5)));
        
        // heading with columns
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(0, 7));
        header.add(new ScaledLabel("Stock"));
        header.add(new ScaledLabel("Shares"));
        header.add(new ScaledLabel("Cost"));
        header.add(new ScaledLabel("Price"));
        header.add(new ScaledLabel("Day Change"));
        header.add(new ScaledLabel("Total Invested"));
        header.add(new ScaledLabel("% of Portfolio"));
        stocksConstraints.gridy++;
        stocksOverviewPanel.add(header, stocksConstraints);


        // metrics for owned stocks
        stocksConstraints.gridwidth = 10;
        for (String ticker : Portfolio.getOwnedStocks().keySet()) {
            if (Portfolio.getOwnedStocks().get(ticker) == 0)
                continue;
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(0, 7));

            // ticker ot stock
            row.add(new ScaledLabel(ticker));

            // number of shares owned
            row.add(new ScaledLabel("" + Portfolio.getOwnedStocks().get(ticker)));

            // average amount spent on stock
            row.add(new ScaledLabel("$" + Portfolio.averageSpentOnStock(ticker)));

            // current stock price
            row.add(new ScaledLabel("$" + Broker.getStock(ticker).getTransactionPrice()));

            // change in stock price
            row.add(new ScaledLabel(Broker.getStock(ticker).getDayChangeFormatted()));

            // total value invested in stock
            double totalValue = Portfolio.getOwnedStocks().get(ticker) * Broker.getStock(ticker).getTransactionPrice();
            row.add(new ScaledLabel("$" + Stock.round(totalValue)));

            // percent of portfolio
            row.add(new ScaledLabel(100 * Stock.round(totalValue / Portfolio.getTotalNetworth()) + "%"));


            stocksConstraints.gridy++;
            stocksOverviewPanel.add(row, stocksConstraints);
        }

        if (stocksOverviewPanel.getComponentCount() == 1) {
            JPanel row = new JPanel();
            row.add(new ScaledLabel("No stocks owned"));
            stocksConstraints.gridy++;
            stocksConstraints.gridwidth = 10;
            stocksOverviewPanel.add(row, stocksConstraints);
        }
        stocksOverviewPanel.setBorder(border);
        add(stocksOverviewPanel, stocksConstraints);
    }
}
