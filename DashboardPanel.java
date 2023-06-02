
/**
 * @version 1.0.0, 8 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        super();
        setBackground(new Color(255, 255, 255));
    }

    public void reload() {
        removeAll();
        revalidate();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        // top panel
        JPanel overviewPanel = new JPanel();
        overviewPanel.setLayout(new GridLayout(0, 1));
        overviewPanel.setPreferredSize(new Dimension(10000, Options.getFont().getSize() * 7));
        overviewPanel.add(
                new ScaledLabel("Overview - " + Portfolio.getSystemName(), (int) (Options.getFont().getSize() * 1.5)));
        overviewPanel.add(new ScaledLabel("Your balance is: $ " + Portfolio.getBalance()));
        overviewPanel.add(new ScaledLabel("Your net worth is: $ " + Portfolio.getTotalNetworth()));
        overviewPanel
                .add(new ScaledLabel("Your total profit is: $ " + Stock.round(Portfolio.getTotalNetworth() - 10000)));
        overviewPanel.add(new ScaledLabel(
                "Intra-day update count: " + Broker.getStock("AAPL").getPriceHistory().getDayHistory().size() + "/30"));
        overviewPanel.add(new ScaledLabel("Date: " + Broker.getDate()));
        overviewPanel.setBorder(border);
        overviewPanel.setMaximumSize(new Dimension(10000, Options.getFont().getSize() * 10));
        add(overviewPanel);

        // bottom panel
        JPanel stocksOverviewPanel = new JPanel();
        stocksOverviewPanel.setLayout(new BoxLayout(stocksOverviewPanel, BoxLayout.PAGE_AXIS));
        stocksOverviewPanel.add(new ScaledLabel("Assets", (int) (Options.getFont().getSize() * 1.5)));
        stocksOverviewPanel.setBorder(border);

        JPanel stocksHeaderLabel = new JPanel();
        stocksHeaderLabel.setLayout(new GridLayout(0, 7));
        stocksHeaderLabel.setMaximumSize(new Dimension(10000, Options.getFont().getSize()));
        stocksHeaderLabel.add(new ScaledLabel("Stock"));
        stocksHeaderLabel.add(new ScaledLabel("Shares"));
        stocksHeaderLabel.add(new ScaledLabel("Cost"));
        stocksHeaderLabel.add(new ScaledLabel("Price"));
        stocksHeaderLabel.add(new ScaledLabel("Day Change"));
        stocksHeaderLabel.add(new ScaledLabel("Total Invested"));
        stocksHeaderLabel.add(new ScaledLabel("% of Portfolio"));
        stocksOverviewPanel.add(stocksHeaderLabel);

        // metrics for owned stocks
        for (String ticker : Portfolio.getOwnedStocks().keySet()) {
            if (Portfolio.getOwnedStocks().get(ticker) == 0)
                continue;
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(0, 7));
            row.setMaximumSize(new Dimension(10000, Options.getFont().getSize()));

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

            stocksOverviewPanel.add(row);
        }

        if (Portfolio.getOwnedStocks().keySet().size() == 0) {
            JPanel row = new JPanel();
            row.add(new ScaledLabel("No stocks owned"));
            stocksOverviewPanel.add(row);
        }

        stocksOverviewPanel.setMaximumSize(new Dimension(10000, 10000));
        add(stocksOverviewPanel);
    }
}
