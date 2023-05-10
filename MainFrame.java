
/**
 * @version 1.0, 8 May 2023
 * @author Dylan Nugyen and Andrew Kim
 */

import java.awt.Dimension;

import javax.swing.*;

public class MainFrame extends JFrame {
	public MainFrame() {
		loadConfig();
		setVisible(true);
	}

	/**
	 * Loads the configuration for the frame
	 */
	public void loadConfig() {
		setTitle("SSS: Stock Simulator on Steroids by Andrew Kim and Dylan Nguyen");
		setMinimumSize(new Dimension(700, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setWindowTitle(String title) {
		setTitle("SSS: Stock Simulator on Steroids by Andrew Kim and Dylan Nguyen - " + title);
	}
}
