public class ClickerTarget {
    private int x, y, speed;
    private double direction;

    public ClickerTarget(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        direction = Math.random() * 2 * Math.PI;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void step(){
        x += speed * Math.cos(direction);
        y += speed * Math.sin(direction);
    }

    public void bounceVertical(){
        direction = Math.PI + (Math.PI - direction);
    }

    public void bounceHorizontal(){
        direction = Math.PI - direction;
    }
}
