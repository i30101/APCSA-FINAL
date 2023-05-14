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
}
