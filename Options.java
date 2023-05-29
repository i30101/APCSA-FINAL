/**
 * @version 1.0.0 26 May 2023
 * @author Andrew Kim and Dylan Nguyen
 * 
 * Manages stockbroker options and stores counts of each share
 */

import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Options {
	private static Hashtable<String, String> options = new Hashtable<>();
	private static Hashtable<String, Integer> shareCounts = new Hashtable<>();

	/**
	 * Loads options from file resources/options.txt
	 * Follows the format <option>:<value>
	 */
	public static void loadOptions() {
		String filepath = "./resources/options.txt";
		try {
			Scanner scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("\n");
			int count = 0;
			while (scanner.hasNext()) {
				String next = scanner.next();
				String key = next.split(":")[0].split("\r")[0];
				String value = next.split(":")[1].split("\r")[0];
				if(count < 5) {
					options.put(key, value);
				}else{
					shareCounts.put(key, Integer.parseInt(value));
				}
				count++;
			}
			scanner.close();
			
			// print hashtables
			// System.out.println(options.toString());
			// System.out.println(shareCounts.toString());
		} catch (Exception e) {
			System.out.println("Could not read file: " + e);
		}
	}


	/**
	 * Writes values of hashtables to txt
	 */
	public static void saveOptions() {
		options.put("networth", "" + Portfolio.getTotalNetworth());


		ArrayList<String> optionsList = new ArrayList<String>();
		// go through options
		for (int i = 0; i < options.size(); i++) {
			String keyName = options.keySet().toArray()[i].toString();
			String keyValue = options.get(keyName);
			optionsList.add(keyName + ":" + keyValue);
		}
		
		// go through shareCounts
		for(int i = 0; i < shareCounts.size(); i++) {
			String keyName = shareCounts.keySet().toArray()[i].toString();
			String keyValue = "" + shareCounts.get(keyName);
			optionsList.add(keyName + ":" + keyValue);
		}
		
		FileWriter writer;
		try {
			writer = new FileWriter("./resources/options.txt");
			for (String line : optionsList) {
				writer.append(line + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing CSV file: " + e);
		}
	}


	/**
	 * Gets the number of shares of given stocks in hashtable
	 * @param ticker ticker of stock
	 * @return number of shares of stock
	 */
	public static int getStockCount(String ticker) {
		return shareCounts.get(ticker);
	}


	/**
	 * Sets the number of shares for a given stock in hashtable
	 * @param stock ticker of stock
	 * @param shares number of shares of stock
	 */
	public static void setStockCount(String stock, int shares) {
		shareCounts.put(stock, shares);
	}

	/**
	 * Returns the font.
	 * 
	 * @return the font.
	 */
	public static Font getFont() {
		return new Font("Arial", Font.PLAIN, Integer.valueOf(options.get("fontsize")));
	}

	/**
	 * Returns the font used with a custom font size
	 * 
	 * @param size the size of the font.
	 * @return the font used by the program.
	 */
	public static Font getFont(int size) {
		return new Font("Arial", Font.PLAIN, size);
	}

	/**
	 * Sets the font used by the program.
	 * 
	 * @param f the font to be used by the program.
	 */
	public static void setFont(Font f) {
		options.put("fontsize", "" + f.getSize());
		saveOptions();
	}

	/**
	 * Sets the status of popups.
	 * 
	 * @param b whether or not popups should be enabled.
	 */
	public static void setPopups(boolean b) {
		String s = b ? "true" : "false";
		options.put("popups", s);
		saveOptions();
	}

	/**
	 * Returns whether or not popups are enabled.
	 * 
	 * @return whether or not popups are enabled.
	 */
	public static boolean popupsEnabled() {
		return options.get("popups").equals("true");
	}

	/**
	 * Sets the fullscreen option
	 * 
	 * @param b whether or not the program should start in fullscreen.
	 */
	public static void setStartFullscreen(boolean b) {
		String s = b ? "true" : "false";
		options.put("startfullscreen", s);
		saveOptions();
	}

	/**
	 * Returns the fullscreen option
	 * 
	 * @return whether or not the program should start in fullscreen.
	 */
	public static boolean getStartFullscreen() {
		return options.get("startfullscreen").equals("true");
	}

	public static void setSimulationSpeed(int speed){
		options.put("simulationspeed", ""+speed);
		saveOptions();
	}

	public static int getSimulationSpeed(){
		return Integer.parseInt(options.get("simulationspeed"));
	}

	public static int getSimulationSpeedScaled(){
		return 2500/Integer.parseInt(options.get("simulationspeed"));
	}

	public static double getNetWorth() {
		return Double.parseDouble(options.get("networth"));
	}
	
}
