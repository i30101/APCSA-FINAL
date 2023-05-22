import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionsPanel extends JPanel {
    public OptionsPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload() {
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

        
        ScaledLabel togglePopups = new ScaledLabel("Toggle buy/sell popups: ");
        togglePopups.setFont(Options.getFont());
        c.gridx = 0;
        c.gridy = 2;
        add(togglePopups, c);

        JRadioButton togglePopupsButton = new JRadioButton();
        togglePopupsButton.setBackground(new Color(230, 230, 230));
        togglePopupsButton.setSelected(Options.popupsEnabled());
        c.gridx = 1;
        add(togglePopupsButton, c);


        ScaledLabel startFullscreen = new ScaledLabel("Start in fullscreen: ");
        startFullscreen.setFont(Options.getFont());
        c.gridx = 0;
        c.gridy = 3;
        add(startFullscreen, c);

        JRadioButton startFullscreenButton = new JRadioButton();
        startFullscreenButton.setBackground(new Color(230, 230, 230));  
        startFullscreenButton.setSelected(Options.getStartFullscreen());
        c.gridx = 1;
        add(startFullscreenButton, c);

        c.gridy++;
        c.gridx = 0;
        Button applyButton = new Button("Apply");
        applyButton.setFont(Options.getFont());
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int fontSize = Integer.parseInt(fontSizeInput.getText());
                    if (fontSize <= 0 && fontSize > 50) 
                        JOptionPane.showMessageDialog(null, "Please enter a valid font size between 1 and 50");
                    else
                        Options.setFont(new Font("Verdana", Font.PLAIN, fontSize));
                    Options.setPopups(togglePopupsButton.isSelected());
                    Options.setStartFullscreen(startFullscreenButton.isSelected());
                    reload();
                    Options.saveOptions();
                    JOptionPane.showMessageDialog(null, "Options saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(applyButton, c);
        revalidate();
        repaint();
    }
}
