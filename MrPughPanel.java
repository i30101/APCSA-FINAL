import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;

public class MrPughPanel extends JPanel {
    public MrPughPanel(){
        super();
        reload();
        revalidate();
        repaint();
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }
    public void reload(){
        removeAll();
        setLayout(new GridLayout(0,1));
        setMaximumSize(getPreferredSize());
        ClickerPanel panel = new ClickerPanel();
        add(panel);
    }
}
