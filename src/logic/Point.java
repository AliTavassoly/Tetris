package logic;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "logic.Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void sum(Point p){
        x += p.getX();
        y += p.getY();
    }

    public void minus(Point p){
        x -= p.getX();
        y -= p.getY();
    }

    public void rotateClockwise() {
        int a00 = 0, a01 = -1, a10 = 1, a11 = 0;
        int lastX = x, lastY = y;
        x = a00 * lastX + a01 * lastY;
        y = a10 * lastX + a11 * lastY;
    }

    public void rotateCounterClockwise() {
        int a00 = 0, a01 = 1, a10 = -1, a11 = 0;
        int lastX = x, lastY = y;
        x = a00 * lastX + a01 * lastY;
        y = a10 * lastX + a11 * lastY;
    }

    public Point getCopy(){
        return new Point(x, y);
    }
}
