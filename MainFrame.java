
/**
 * @version 1.0.0, 8 May 2023
 * @author Dylan Nguyen and Andrew Kim
 */

import java.awt.Dimension;
import java.awt.event.*;

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
		// https://stackoverflow.com/questions/6084039/create-custom-operation-for-setdefaultcloseoperation
		WindowListener listener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// wait until safe to close
				while (!Options.isSafeToClose() || !Broker.isSafeToClose()) {
					System.out.println("Unsafe!");
					try {
						Thread.sleep(10);
					} catch (InterruptedException e1) {
						/* nyeh! */}
				}
			
				System.exit(0);
			}
		};
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(listener);
	}

	public void setWindowTitle(String title) {
		setTitle("SSS: Stock Simulator on Steroids by Andrew Kim and Dylan Nguyen - " + title);
	}
}
