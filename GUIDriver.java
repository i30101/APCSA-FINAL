
/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUIDriver {
	public static void main(String[] args) throws IOException {
		// create the frame
		MainFrame mainGUI = new MainFrame();

		// create the container; background color is the sidebar color
		JPanel gridPanel = new JPanel();
		mainGUI.add(gridPanel);

		// buttons placed in arraylist to allow for programmatic selection
		ArrayList<SidebarButton> buttons = new ArrayList<SidebarButton>();

		// use a 2x3 grid layout
		gridPanel.setLayout(new GridBagLayout());
		gridPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;

		// create the sidebar buttons
		SidebarButton dashboardButton = new SidebarButton(new File("resources/dashboard.png"));
		dashboardButton.setText("dashboard"); // used kind of like a button id
		gridPanel.add(dashboardButton, c);
		buttons.add(dashboardButton);

		SidebarButton stocksButton = new SidebarButton(new File("resources/stocks.png"));
		stocksButton.setText("stocks");
		c.gridy = 1; // move to the next row
		gridPanel.add(stocksButton, c);
		buttons.add(stocksButton);

		SidebarButton optionsButton = new SidebarButton(new File("resources/options.png"));
		optionsButton.setText("options");
		c.gridy = 2;
		gridPanel.add(optionsButton, c);
		buttons.add(optionsButton);

		SidebarButton matthewButton = new SidebarButton(new File("resources/matthew.png"));
		matthewButton.setText("matthew");
		c.gridy = 3;
		gridPanel.add(matthewButton, c);
		buttons.add(matthewButton);

		// create the display panel, which is the main content area
		DisplayPanel displayPanel = new DisplayPanel();
		displayPanel.setPreferredSize(new Dimension(600, 550));
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 10;
		c.ipadx = 3;
		c.gridheight = buttons.size();
		gridPanel.add(displayPanel, c);

		// resize the display panel when the window is resized
		gridPanel.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				displayPanel.setPreferredSize(new Dimension(gridPanel.getWidth() - 85, gridPanel.getHeight() - 10));
			}
		});

		// add action listeners to the buttons to switch display on click
		for (SidebarButton button : buttons) {
			button.addActionListener(event -> {
				for (SidebarButton b : buttons) {
					b.deselect();
				}
				button.select();
				if (button.getText().equals("dashboard")) {
					displayPanel.displayDashboard();
				} else if (button.getText().equals("stocks")) {
					displayPanel.displayStocks();
				} else if (button.getText().equals("options")) {
					displayPanel.displayOptions();
				} else if (button.getText().equals("matthew")) {
					displayPanel.displayMatthew();
				}
			});
		}

		mainGUI.add(gridPanel);
		mainGUI.setSize(new Dimension(700, 600));

		// set the default display to the dashboard
		dashboardButton.select();
		displayPanel.displayDashboard();
	}
}