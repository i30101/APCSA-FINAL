
/**
 * @version 1.0.0 9 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StockOptionsWindow extends JFrame {
	Runtime runtime = Runtime.getRuntime();

	public StockOptionsWindow(Stock stock, int x, int y) {
		super();

		// set up window
		setLocation(x, y);
		setTitle("Stock Options for " + stock.getTicker() + " - " + stock.getName());
		setSize(450 * (Options.getFont().getSize() / 10), 200 * (Options.getFont().getSize() / 10));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		reload(stock);
	}

	public void reload(Stock stock){
		revalidate();
		repaint();
		// set up layout
		GridLayout layout = new GridLayout(0, 3);
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(layout);

		// add components

		// first row
		ScaledLabel ticker = new ScaledLabel(stock.getTicker());
		panel.add(ticker);

		ScaledLabel currentPrice = new ScaledLabel("$ " + stock.getTransactionPrice());
		panel.add(currentPrice);
		ScaledLabel change = new ScaledLabel();
		// change color based on if change is positive or negative
		if (stock.getDayChange() > 0) {
			change.setText(
					"<html><font color='green'>+" + stock.getDayChange() + " (+" + stock.getDayChangePercent() + "%)"
							+ "</font></html>");
		} else if (stock.getDayChange() < 0) {
			change.setText("<html><font color='red'>" + stock.getDayChange() + " (" + stock.getDayChangePercent() + "%)"
					+ "</font></html>");
		} else {
			change.setText("Change: " + stock.getDayChange());
		}
		panel.add(change);

		ScaledLabel balance = new ScaledLabel("Current Balance: ");
		panel.add(balance);

		ScaledLabel balanceAmount = new ScaledLabel("$ " + Portfolio.getBalance());
		panel.add(balanceAmount);

		double percentage = (stock.getTransactionPrice()/Portfolio.getBalance());
		percentage = Math.round(percentage * 1000) / 10.0;
		ScaledLabel percentOfTotal = new ScaledLabel(stock.getTicker()+" : " + percentage + "%");
		panel.add(percentOfTotal);

		ScaledLabel owned = new ScaledLabel("Owned: ");
		panel.add(owned);

		ScaledLabel amountOwned = new ScaledLabel(""+Portfolio.getStockAmount(stock.getTicker()));
		panel.add(amountOwned);

		ScaledLabel amountOwnedWorth = new ScaledLabel("$ "+Broker.formatBalance(stock.getTransactionPrice() * Portfolio.getStockAmount(stock.getTicker())));
		panel.add(amountOwnedWorth);

		ScaledLabel label = new ScaledLabel("Buy: ");
		panel.add(label);

		JTextField buyAmountInput = new JTextField();
		buyAmountInput.setFont(Options.getFont());
		buyAmountInput.setText("1");
		panel.add(buyAmountInput);

		Button buyButton = new Button("Buy");
		buyButton.setFont(Options.getFont());
		buyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// https://www.tutorialspoint.com/java/character_isdigit.htm
				for (int i = 0; i < buyAmountInput.getText().length(); i++) {
					if (!Character.isDigit(buyAmountInput.getText().charAt(i))) {
						JOptionPane.showMessageDialog(null,"Please enter a valid number.");
						return;
					}
				}
				int amount = Integer.parseInt(buyAmountInput.getText());
				double cost = Portfolio.buyStock(stock.getTicker(), amount);
				if (cost != -1) {
					if(Options.popupsEnabled()) JOptionPane.showMessageDialog(null,"Successfully bought " + amount + " shares of " + stock.getTicker() + " for " + cost);
					remove(panel);
					reload(stock);
				} else {
					JOptionPane.showMessageDialog(null,"Could not buy stock. Please enter a lower number.");
				};
			}
		});
		panel.add(buyButton);

		ScaledLabel label2 = new ScaledLabel("Sell: ");
		panel.add(label2);

		JTextField sellAmountInput = new JTextField();
		sellAmountInput.setFont(Options.getFont());
		sellAmountInput.setText("1");
		panel.add(sellAmountInput);

		Button sellButton = new Button("Sell");
		sellButton.setFont(Options.getFont());
		sellButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// https://www.tutorialspoint.com/java/character_isdigit.htm
				for (int i = 0; i < sellAmountInput.getText().length(); i++) {
					if (!Character.isDigit(sellAmountInput.getText().charAt(i))) {
						JOptionPane.showMessageDialog(null,"Please enter a valid number.");
						return;
					}
				}
				int amount = Integer.parseInt(sellAmountInput.getText());
				double cost = Broker.formatBalance(Portfolio.sellStock(stock.getTicker(), amount));
				if (cost != -1) {
					if(Options.popupsEnabled()) JOptionPane.showMessageDialog(null,"Successfully sold " + amount + " shares of " + stock.getTicker() + " for " + cost);
					remove(panel);
					reload(stock);
				} else {
					JOptionPane.showMessageDialog(null,"Could not sell stock. Please enter a lower number.");
				};
			}
		});
		panel.add(sellButton);

		ScaledLabel viewGraphLabel = new ScaledLabel("View graph");
		panel.add(viewGraphLabel);

		JComboBox<String> graphOptions = new JComboBox<String>();
		graphOptions.setFont(Options.getFont());
		graphOptions.addItem("day");
		graphOptions.addItem("month");
		graphOptions.addItem("year");
		panel.add(graphOptions);

		Button viewGraph = new Button("View graph");
		viewGraph.setFont(Options.getFont());
		panel.add(viewGraph);
		viewGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					viewGraph.setLabel("Loading graph...");
					System.out.println("Trying to run "
							+ ("python ./data/graph.py " + graphOptions.getSelectedItem() + " " + stock.getTicker()));
					String[] command = { "python", "./data/graph.py", (String) graphOptions.getSelectedItem(),
							stock.getTicker() };
					// sourced from
					// https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program
					// execute python script to generate graph and grab the error stream if it
					// exists
					InputStream output = runtime.exec(command).getErrorStream();
					int data = output.read();
					String error = "";
					while (data != -1) {
						error += (char) data;
						data = output.read();
						// if there is a desync, there is probably missing data
						if (error.indexOf("IndexError") != -1) {
							JOptionPane.showMessageDialog(null, "Error: No data for " + graphOptions.getSelectedItem()
									+ " graph of " + stock.getTicker());
							break;
						}
						// catching 'no module' means that the libraries are not installed
						if (error.indexOf("no module") != -1) {
							JOptionPane.showMessageDialog(null,
									"Error: Libraries not installed. Please run install.bat.");
							break;
						}
						// general error catching
						if (error.indexOf("Error") != -1) {
							JOptionPane.showMessageDialog(null, "An error occured.");
							break;
						}

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				viewGraph.setLabel("View graph");
			}

		});

		revalidate();
		repaint();
	}
}
