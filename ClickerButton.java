import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.Image;

public class ClickerButton {
    private int x, y, width, height;
    private Image image;
    private Color color;
    private String id, text;

    public ClickerButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ClickerButton(int x, int y, int width, int height, Color color, String id, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.id = id;
        this.text = text;
    }

    public ClickerButton(int x, int y, int width, int height, File image, String id, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {
            this.image = ImageIO.read(image);
        } catch (Exception e) {
            System.out.println(e);
        }

        this.id = id;
        this.text = text;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public Image getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
