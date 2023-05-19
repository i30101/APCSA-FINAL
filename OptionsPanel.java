import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionsPanel extends JPanel{
	public OptionsPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload(){
		removeAll();
		setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;

        ScaledLabel optionsLabel = new ScaledLabel("Options");
        optionsLabel.setFont(Options.getFont());
        add(optionsLabel, c);

        ScaledLabel fontSizeLabel = new ScaledLabel("Font size: ");
        fontSizeLabel.setFont(Options.getFont());
		c.gridy = 1;
        add(fontSizeLabel, c);

        JTextField fontSizeInput = new JTextField(4);
        fontSizeInput.setFont(Options.getFont());
        fontSizeInput.setText("" + Options.getFont().getSize());
        c.gridx = 1;
        add(fontSizeInput, c);

        c.gridy = 2;
        c.gridx = 0;
        Button applyButton = new Button("Apply");
        applyButton.setFont(Options.getFont());
        applyButton.addActionListener(e -> {
            try {
                int fontSize = Integer.parseInt(fontSizeInput.getText());
                if (fontSize > 0 && fontSize < 50) {
                    Options.setFont(new Font("Verdana", Font.PLAIN, fontSize));
                    reload();
                }else{
                    JOptionPane.showMessageDialog(null, "Please enter a valid font size between 1 and 50");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(applyButton, c);
		revalidate();
		repaint();
    }
}
