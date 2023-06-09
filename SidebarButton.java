/**
 * @version 1.0.0, 8 May 2023
 * @author Andrew Kim and Dylan Nguyen
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SidebarButton extends JButton {
    private boolean isSelected = false;
    private final int IMAGE_WIDTH = 32;
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
            label = new JLabel(new ImageIcon(ImageIO.read(image).getScaledInstance(IMAGE_WIDTH, IMAGE_WIDTH, Image.SCALE_FAST)));
        } catch (IOException e) {
            label = new ScaledLabel("Error");
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
