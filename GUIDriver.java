/**
 * @version 1.0.0, 8 May 2023
 * @author Dylan Nguyen and Andrew Kim
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.ArrayList;

public class GUIDriver {
	private String selected = "dashboard";

	public GUIDriver() {
		// create the frame
		MainFrame mainGUI = new MainFrame();
		mainGUI.setIconImage(new ImageIcon("resources/icon.png").getImage());

		// create the container; background color is the sidebar color
		JPanel gridPanel = new JPanel();
		mainGUI.add(gridPanel);

		// load options before render
		Options.loadOptions();

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

		SidebarButton pughButton = new SidebarButton(new File("resources/clicker/best_girl.jpg"));
		pughButton.setText("mr pugh");
		c.gridy++;
		gridPanel.add(pughButton, c);
		buttons.add(pughButton);

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
				displayPanel.revalidate();
			}
		});

		// add action listeners to the buttons to switch display on click
		for (SidebarButton button : buttons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (SidebarButton b : buttons)
						b.deselect();
					selected = button.getText();
					button.select();
					displayPanel.openByID(selected);
					mainGUI.setWindowTitle(selected);
				}
			});
		}

		mainGUI.add(gridPanel);
		mainGUI.setSize(mainGUI.getMinimumSize().width + 10, mainGUI.getMinimumSize().height + 10);

		// Open fullscreen if option is enabled
		if (Options.getStartFullscreen())
			mainGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);

		// set the default display to the dashboard
		dashboardButton.select();
		displayPanel.displayDashboard();
		displayPanel.reloadOpenPanel();
		mainGUI.setWindowTitle(selected);

		// reload the displayPanel every couple seconds for live updates
		// made with help from github copilot
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(displayPanel.getCurrentPanel() != "options" && displayPanel.getCurrentPanel() != "mr pugh")
					displayPanel.openByID(selected);
			}
		});
		timer.setRepeats(true);
		timer.start();
	}
}