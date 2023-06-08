import javax.swing.JPanel;
import java.awt.GridLayout;

public class MrPughPanel extends JPanel {
    ClickerPanel panel;

    public MrPughPanel() {
        super();
        reload();
        revalidate();
        repaint();
    }

    public void reload() {
        removeAll();
        setLayout(new GridLayout(0, 1));
        setMaximumSize(getPreferredSize());
        panel = new ClickerPanel();
        add(panel);
    }
}
