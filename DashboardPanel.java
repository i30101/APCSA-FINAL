import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        super();
        int color = 230;
        setBackground(new Color(color, color, color));
    }

    public void reload(){
        setLayout(new GridLayout(0,1));
        removeAll();
        add(new ScaledLabel("Hello, "+Portfolio.getSystemName()+"    Balance: $ "+Broker.formatMoney(Portfolio.getBalance())+"\n    Net Worth: $ "+Broker.formatMoney(Portfolio.totalNetworth())+"\n"));
        GridLayout c = new GridLayout(0,4);
        JPanel ownedStocks = new JPanel();
        ownedStocks.setLayout(c);
        for(String key : Portfolio.getOwnedStocks().keySet()){
            ownedStocks.add(new ScaledLabel(Broker.getStock(key).getName()));
            ownedStocks.add(new ScaledLabel(key));
            ownedStocks.add(new ScaledLabel("Owned: " + Portfolio.getOwnedStocks().get(key)));
            ownedStocks.add(new ScaledLabel("Value: $ " + Broker.formatMoney(Portfolio.getOwnedStocks().get(key) * Broker.getStock(key).getTransactionPrice())));
        }
        add(ownedStocks);
    }
}
