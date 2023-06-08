import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClickerPanel extends JPanel implements MouseListener {
    final int IMAGE_SIZE = 64;
    final int PUGH_PER_PUGH = 20;
    final double DOLLARS_PER_PUGH = .1;
    final int PUGH_MOVESPEED = 10;
    final int FPS = 30;
    final int TICKRATE = 1000 / FPS;

    private int redBuffTimer = 0, pinkBuffTimer = 0;

    Image img;
    Timer timer;

    private static int pughPoints = 100;

    private ArrayList<ClickerTarget> targets;
    private ArrayList<ClickerButton> buttons;

    public ClickerPanel() {
        removeAll();
        revalidate();
        setBackground(new Color(0, 0, 0));
        targets = new ArrayList<>();
        try {
            img = ImageIO.read(new File("resources/clicker/best_teacher.jpg"));
        } catch (IOException e) {
            /* we do not care */ }
        Timer timer = new Timer(TICKRATE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.setRepeats(true);
        timer.start();
        addMouseListener(this);

        ClickerButton redCup = new ClickerButton(0, 100, 100, 100, new File("resources/clicker/red_cup.png"), "redCup",
                "A red cup\nCosts 100 Pugh Points\n\"whats a for loop??\"");
        ClickerButton pinkCup = new ClickerButton(0, 200, 100, 100, new File("resources/clicker/pink_cup.png"),
                "pinkCup",
                "A pink cup\nCosts 100 Pugh Points\n\"can you do this lab for me real quick\"");
        ClickerButton greenCup = new ClickerButton(0, 300, 100, 100, new File("resources/clicker/green_cup.png"),
                "greenCup",
                "A green cup\n'i am above everybody'");

        buttons = new ArrayList<>();
        buttons.add(redCup);
        buttons.add(pinkCup);
        buttons.add(greenCup);
    }

    public void clickPoint(int x, int y) {
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).getX() < x && targets.get(i).getX() + IMAGE_SIZE > x && targets.get(i).getY() < y
                    && targets.get(i).getY() + IMAGE_SIZE > y) {
                double redBuff = (redBuffTimer > 0) ? 2 : 1;
                double pinkBuff = pinkBuffTimer > 0 ? 1.5 : 1;
                Portfolio.addMoney(
                        (int) ((int) (1 + (DOLLARS_PER_PUGH * Portfolio.getStockAmount("PUGH"))) * redBuff * pinkBuff));
                targets.remove(i);
                pughPoints++;
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getX() < x && buttons.get(i).getX() + buttons.get(i).getWidth() > x
                    && buttons.get(i).getY() < y && buttons.get(i).getY() + buttons.get(i).getHeight() > y) {
                ClickerButton button = buttons.get(i);
                if (button.getId().equals("redCup")) {
                    if (pughPoints >= 100) {
                        pughPoints -= 100;
                        // 15 second buff
                        redBuffTimer = 15 * 1000 / TICKRATE;
                    } else {
                        JOptionPane.showMessageDialog(getComponentPopupMenu(),
                                "You really think Mr. Pugh is coming over to you? (insufficient pugh points)");
                    }
                } else if (button.getId().equals("pinkCup")) {
                    if (pughPoints >= 100) {
                        pughPoints -= 100;
                        // 30 second buff
                        pinkBuffTimer = 30 * 1000 / TICKRATE;
                    } else {
                        JOptionPane.showMessageDialog(getComponentPopupMenu(),
                                "figure it out yourself... (insufficient pugh points)");
                    }
                }
            }
        }
    }

    // https://cs.lmu.edu/~ray/notes/javagraphics/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawString("Mr. Pugh Clicker", 0, 20);
        g.drawString("virtual meaningless dollars: " + Portfolio.getBalance(), 0, 40);
        g.drawString("even more meaningless dollars (pugh points): " + pughPoints, 0, 60);

        // set amount of mr pughs
        while (targets.size() > 1 + (Portfolio.getStockAmount("PUGH") / PUGH_PER_PUGH)) {
            targets.remove(targets.size() - 1);
        }
        while (targets.size() < 1 + (Portfolio.getStockAmount("PUGH") / PUGH_PER_PUGH)) {
            ClickerTarget target = new ClickerTarget((int) (Math.random() * getWidth()),
                    (int) (Math.random() * getHeight()), PUGH_MOVESPEED);
            while (target.getX() + IMAGE_SIZE > getWidth() || target.getY() + IMAGE_SIZE > getHeight()) {
                target = new ClickerTarget((int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()), PUGH_MOVESPEED);
            }
            targets.add(target);
        }
        // draw all mr pughs
        for (int i = 0; i < targets.size(); i++) {
            g.drawImage(img, targets.get(i).getX(), targets.get(i).getY(), IMAGE_SIZE, IMAGE_SIZE, null);
            // step all 
            targets.get(i).step();
            // bounce
            if (targets.get(i).getX() < 0 || targets.get(i).getX() + IMAGE_SIZE > getWidth()) {
                targets.get(i).bounceHorizontal();
            }
            if (targets.get(i).getY() < 0 || targets.get(i).getY() + IMAGE_SIZE > getHeight()) {
                targets.get(i).bounceVertical();
            }
        }
        // draw all buttons
        for (int i = 0; i < buttons.size(); i++) {
            g.drawImage(buttons.get(i).getImage(), buttons.get(i).getX(), buttons.get(i).getY(),
                    buttons.get(i).getWidth(), buttons.get(i).getHeight(), null);
            g.setColor(Color.WHITE);
            if (buttons.get(i).getId().equals("redCup") && redBuffTimer > 0) {
                buttons.get(i).setText(buttons.get(i).getText().split("\n")[0] + "\nRed Buff (2x): "
                        + (redBuffTimer / (1000 / TICKRATE)));
                redBuffTimer--;
            }
            if (buttons.get(i).getId().equals("pinkCup") && pinkBuffTimer > 0) {
                buttons.get(i).setText(buttons.get(i).getText().split("\n")[0] + "\nPink Buff (1.5x): "
                        + (pinkBuffTimer / (1000 / TICKRATE)));
                pinkBuffTimer--;
            }
            for (int line = 0; line < buttons.get(i).getText().split("\n").length; line++) {
                g.drawString(buttons.get(i).getText().split("\n")[line],
                        buttons.get(i).getX() + buttons.get(i).getWidth() + 5,
                        buttons.get(i).getY() + (20 * line) + 20);

            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        clickPoint(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
