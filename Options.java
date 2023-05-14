import java.awt.Font;

public class Options {
	private static Font font = new Font("Verdana", Font.PLAIN, 10);

	public static Font getFont() {
		return font;
	}

	public static void setFont(Font f) {
		font = f;
	}
}
