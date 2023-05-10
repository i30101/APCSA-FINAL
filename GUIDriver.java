/**
 * @version 1.0, 8 May 2023
 * @author Dylan Nguyen and Andrew Kim
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class GUIDriver {
	public GUIDriver() {
		// create the frame
		MainFrame mainGUI = new MainFrame();

		// create the container; background color is the sidebar color
		JPanel gridPanel = new JPanel();
		mainGUI.add(gridPanel);

		// buttons placed in arraylist to allow for programmatic selection
		ArrayList<SidebarButton> buttons = new ArrayList<SidebarButton>();

		// use a 2x3 grid layout
		gridPanel.setLayout(new GridBagLayout());
		gridPanel.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.SOUTH;

		// create the sidebar buttons
		SidebarButton dashboardButton = new SidebarButton(new File("resources/dashboard.png"));
		dashboardButton.setText("dashboard"); // used kind of like a button id
		c.gridy = 0;
		gridPanel.add(dashboardButton, c);
		buttons.add(dashboardButton);

		SidebarButton stocksButton = new SidebarButton(new File("resources/stocks.png"));
		stocksButton.setText("stocks");
		c.gridy++; // move to the next row
		gridPanel.add(stocksButton, c);
		buttons.add(stocksButton);

		SidebarButton matthewButton = new SidebarButton(new File("resources/matthew.png"));
		matthewButton.setText("matthew");
		c.gridy++;
		gridPanel.add(matthewButton, c);
		buttons.add(matthewButton);

		SidebarButton optionsButton = new SidebarButton(new File("resources/options.png"));
		optionsButton.setText("options");
		c.gridy++;
		gridPanel.add(optionsButton, c);
		buttons.add(optionsButton);

		// create the display panel, which is the main content area
		DisplayPanel displayPanel = new DisplayPanel();
		displayPanel.setPreferredSize(new Dimension(600, 550));
		c.gridx = 1; // 2nd column
		c.gridy = 0;
		c.gridheight = buttons.size();
		gridPanel.add(displayPanel, c);

		// resize the display panel when the window is resized
		gridPanel.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				displayPanel.setPreferredSize(new Dimension(gridPanel.getWidth() - 76, gridPanel.getHeight()));
				// 85 and 10 are magic numbers that work
			}
		});

		// https://www.w3schools.blog/java-every-second
		// required to update the display panel every half second because jpanel so cool yayyy
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				displayPanel.revalidate();
			}
		}, 0, 500);

		// add action listeners to the buttons to switch display on click
		for (SidebarButton button : buttons) {
			button.addActionListener(event -> {
				for (SidebarButton b : buttons)
					b.deselect();
				button.select();
				displayPanel.openByID(button.getText());
			});
		}

		mainGUI.add(gridPanel);
		mainGUI.setSize(mainGUI.getMinimumSize().width+10, mainGUI.getMinimumSize().height+10);

		// set the default display to the dashboard
		dashboardButton.select();
		displayPanel.displayDashboard();
	}
}