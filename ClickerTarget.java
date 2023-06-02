public class ClickerTarget {
    private int x, y, timer;

    public ClickerTarget(int x, int y){
        this.x = x;
        this.y = y;
        timer = 0;
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

    public int getTimer() {
        return timer;
    }

    public void setTimer(int t){
        timer = t;
    }

    public void incrementTimer(){
        timer++;
    }
}
