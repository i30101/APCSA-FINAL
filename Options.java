import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

public class Options {
	private static Hashtable<String, String> options = new Hashtable<>();

	/**
	 * Loads options from file resources/options.txt
	 * Follows the format <option>:<value>
	 */
	public static void loadOptions() {
		String filepath = "./resources/options.txt";
		try {
			Scanner scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("\n");
			while (scanner.hasNext()) {
				String next = scanner.next();
				String key = next.split(":")[0];
				String value = next.split(":")[1];
				options.put(key, value);
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Could not read file: " + e);
		}
	}

	public static void saveOptions() {
		String[] optionsString = new String[options.size()];
		for (int i = 0; i < options.size(); i++) {
			String keyName = options.keySet().toArray()[i].toString();
			String keyValue = options.get(keyName);
			optionsString[i] = keyName + ":" + keyValue;
		}
		FileWriter writer;
		try {
			writer = new FileWriter("./resources/options.txt");
			for (String line : optionsString) {
				writer.append(line + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing CSV file: " + e);
		}
	}

	/**
	 * Returns the font.
	 * 
	 * @return the font.
	 */
	public static Font getFont() {
		return new Font("Verdana", Font.PLAIN, Integer.parseInt(options.get("fontsize").split("\r")[0]));
	}

	/**
	 * Returns the font used with a custom font size
	 * 
	 * @param size the size of the font.
	 * @return the font used by the program.
	 */
	public static Font getFont(int size) {
		return new Font("Verdana", Font.PLAIN, size);
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
}
