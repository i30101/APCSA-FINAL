import java.awt.Font;

public class Options {
	private static Font font = new Font("Verdana", Font.PLAIN, 10);
	private static boolean popups = true;

	/**
	 * Returns the font used by the program.
	 * @return the font used by the program.
	 */
	public static Font getFont() {
		return font;
	}

	/**
	 * Sets the font used by the program.
	 * @param f the font to be used by the program.
	 */
	public static void setFont(Font f) {
		font = f;
	}

	/**
	 * Sets the status of popups.
	 * @param b whether or not popups should be enabled.
	 */
	public static void setPopups(boolean b){
		popups = b;
	}

	/**
	 * Returns whether or not popups are enabled.
	 * @return whether or not popups are enabled.
	 */
	public static boolean popupsEnabled(){
		return popups;
	}

}
