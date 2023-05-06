import java.awt.*;

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
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
