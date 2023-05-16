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

	public StockOptionsWindow(Stock stock, int x, int y){
		super();
		setLocation(x, y);
		setTitle("Stock Options for " + stock.getTicker() + " - " + stock.getName());
		setSize(400 * (Options.getFont().getSize()/10), 200 * (Options.getFont().getSize()/10));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);

		GridLayout layout = new GridLayout(0,3);
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(layout);

		ScaledLabel ticker = new ScaledLabel(stock.getTicker());
		panel.add(ticker);

		ScaledLabel currentPrice = new ScaledLabel("$ " + stock.getTransactionPrice());
		panel.add(currentPrice);
		ScaledLabel change = new ScaledLabel();
		if (stock.getDayChange() > 0) {
			change.setText("<html><font color='green'>+" + stock.getDayChange() + " (+" + stock.getDayChangePercent() + "%)"
					+ "</font></html>");
		} else if (stock.getDayChange() < 0) {
			change.setText("<html><font color='red'>" + stock.getDayChange() + " (" + stock.getDayChangePercent() + "%)"
					+ "</font></html>");
		} else {
			change.setText("Change: " + stock.getDayChange());
		}
		panel.add(change);
		


		ScaledLabel label = new ScaledLabel("Buy: ");
		panel.add(label);
		JTextField buyAmountInput = new JTextField();
		buyAmountInput.setFont(Options.getFont());
		buyAmountInput.setText("1");
		panel.add(buyAmountInput);
		Button buyButton = new Button("Buy");
		buyButton.setFont(Options.getFont());
		panel.add(buyButton);

		ScaledLabel label2 = new ScaledLabel("Sell: ");
		panel.add(label2);
		JTextField sellAmountInput = new JTextField();
		sellAmountInput.setFont(Options.getFont());
		sellAmountInput.setText("1");
		panel.add(sellAmountInput);
		Button sellButton = new Button("Sell");
		sellButton.setFont(Options.getFont());
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
					System.out.println("Trying to run "+("python ./data/graph.py " + graphOptions.getSelectedItem() + " " + stock.getTicker()));
					String[] command = {"python", "./data/graph.py", (String) graphOptions.getSelectedItem(), stock.getTicker()};
					// sourced from https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program 
					InputStream output = runtime.exec(command).getErrorStream();
					int data = output.read();
					String error = "";
					while(data != -1) {
						error += (char) data;
						data = output.read();
						if(error.indexOf("IndexError") != -1) {
							JOptionPane.showMessageDialog(null, "Error: No data for "+graphOptions.getSelectedItem()+" graph of "+stock.getTicker());
							break;
						}
						if(error.indexOf("no module") != -1){
							JOptionPane.showMessageDialog(null, "Error: Libraries not installed. Please run install.bat.");
							break;
						}
						if(error.indexOf("Error") != -1){
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
	public static void main(String[] args) {
		new StockOptionsWindow(new Stock("AAPL", "Apple", "Technology"), 0, 0);
	}
}
