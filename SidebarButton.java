/**
 * @author Andrew Kim and Dylan Nguyen
 * @version 1.0, 8 May 2023
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SidebarButton extends JButton {
    private boolean isSelected = false;
    JPanel panel;

    public SidebarButton(File image) {
        super();
        setFont(new Font("Arial", Font.PLAIN, 0));
        setSize(new Dimension(50, 50));
        setContentAreaFilled(false);
        setBorderPainted(false);
        panel = new JPanel();
        JLabel label;
        try {
            label = new JLabel(new ImageIcon(ImageIO.read(image)));
        } catch (IOException e) {
            label = new JLabel("Error");
            e.printStackTrace();
        }
        add(panel);
        panel.add(label);
        deselect();
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public void select(){
        isSelected = true;
        panel.setBackground(Color.LIGHT_GRAY);
    }

    public void deselect(){
        isSelected = false;
        panel.setBackground(Color.WHITE);
    }
}
