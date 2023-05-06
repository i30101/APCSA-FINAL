import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class StockCreatorGUI extends JFrame {
	
	public StockCreatorGUI(){
		loadConfig();
		GridLayout layout = new GridLayout(4, 2);
		setLayout(layout);
		// Create labels
		JLabel stockNameLabel = new JLabel("Stock Name: ");
		JLabel stockTickerLabel = new JLabel("Stock Ticker: ");
		JLabel stockIndustryLabel = new JLabel("Stock Industry: ");

		// Create input boxes
		JTextField stockName = new JTextField();
		JTextField stockTicker = new JTextField();
		JTextField stockIndustry = new JTextField();
		
		// adjust size of input boxes
		stockName.setPreferredSize(new Dimension(100, 20));
		stockTicker.setPreferredSize(new Dimension(100, 20));
		stockIndustry.setPreferredSize(new Dimension(100, 20));

		// Create button
		JButton createStock = new JButton("Create Stock");

		createStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Stock stock = new Stock(stockName.getText(), stockTicker.getText(), stockIndustry.toString());
				if(stockName.getText().equals("") || stockTicker.getText().equals("") || stockIndustry.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill out all fields.");
				} else {
					if(Stocks.addStock(stock)){
						JOptionPane.showMessageDialog(null, "Stock created.");
						System.out.println(Stocks.getStocksString());
					} else{
						JOptionPane.showMessageDialog(null, "Stock already exists.");
					}
				}
			}
		});

		// Create button
		JButton startSimulation = new JButton("Start Simulation");

		startSimulation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Stocks.getStocks().size() == 0) {
					JOptionPane.showMessageDialog(null, "Please create at least one stock.");
				} else {
					dispose();
				}
			}
		});

		add(stockNameLabel);
		add(stockName);
		add(stockTickerLabel);
		add(stockTicker);
		add(stockIndustryLabel);
		add(stockIndustry);
		add(createStock);
		add(startSimulation);

		setVisible(true);
	}

	/**
	 * Loads the configuration for the frame
	 */
	public void loadConfig(){
		setTitle("SSS: Stock Simulator on Steroids by Andrew Kim and Dylan Nguyen");
		setSize(300, 150);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
