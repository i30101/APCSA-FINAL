/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Dimension;

import javax.swing.*;

public class MainFrame extends JFrame {
	public MainFrame(){
		loadConfig();
		setVisible(true);
	}

	/**
	 * Loads the configuration for the frame
	 */
	public void loadConfig(){
		setTitle("SSS: Stock Simulator on Steroids by Andrew Kim and Dylan Nguyen");
		setMinimumSize(new Dimension(650, 550));
		setMaximumSize(new Dimension(1000, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
