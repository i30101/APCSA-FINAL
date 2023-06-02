/**
 * @version 1.0.0, 29, May 2023
 * @author Dylan Nguyen and Andrew Kim
 */

import javax.swing.JLabel;

public class ScaledLabel extends JLabel {
	public ScaledLabel(){
		super();
		setFont(Options.getFont());
	}

	public ScaledLabel(String text){
		super(text);
		setFont(Options.getFont());
	}

	public ScaledLabel(String text, int fontSize){
		super(text);
		setFont(Options.getFont(fontSize));
	}
}
