package logic;

import util.TetrisException;

import java.awt.*;
import java.util.ArrayList;

public class Piece {
    private ArrayList<Point> points;
    int id, n;
    private Point center, start;
    private Color color;

    private Game parent;

    public Piece(Game parent, int id, int n, ArrayList<Point> points, Point start, Color color) {
        this.parent = parent;
        this.id = id;
        this.n = n;
        this.points = points;
        this.start = start;
        this.color = color;

        if (n == 3)
            center = new Point(1, 1);
        if (n == 4)
            center = new Point(0, 0);
    }

    public int getId() {
        return id;
    }

    public int getN(){
        return n;
    }

    public Color getColor(){
        return color;
    }

    public Point getStart() {
        return start;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Point> getPointsInBoard() {
        ArrayList<Point> ans = new ArrayList<Point>();
        for (Point point : points) {
            point.sum(start);
            ans.add(point.getCopy());
            point.minus(start);
        }
        return ans;
    }

    public void rotateClockwise() throws Exception {
        if (n == 2)
            return;
        ArrayList<Point> points1 = new ArrayList<>();

        for (Point point : points)
            points1.add(point.getCopy());

        for (Point point : points) {
            Point point1 = point.getCopy();

            point.minus(center);
            point.rotateClockwise();
            point.sum(center);

            point.sum(start);
            if(point.getX() < 0 || point.getX() >= parent.getN() || point.getY() < 0 || point.getY() >= parent.getM()){
                points.clear();
                for(Point point2 : points1)
                    points.add(point2.getCopy());
                throw new TetrisException("Cant rotate counter clockwise!");
            } else if(!parent.isOK(point.getX(), point.getY(), id) || (n != 4 && !parent.isOK(point.getX(), point1.getY(), id))) {
                points.clear();
                for(Point point2 : points1)
                    points.add(point2.getCopy());
                throw new TetrisException("Cant rotate counter clockwise!");
            }
            point.minus(start);
        }
        parent.updateBoard();
    }

    public void rotateCounterClockwise() throws Exception{
        if (n == 2)
            return;
        ArrayList<Point> points1 = new ArrayList<>();
        for (Point point : points)
            points1.add(point.getCopy());
        for (Point point : points) {
            Point point1 = new Point(point.getX(), point.getY());

            point.minus(center);
            point.rotateCounterClockwise();
            point.sum(center);

            point.sum(start);
            if(point.getX() < 0 || point.getX() >= parent.getN() || point.getY() < 0 || point.getY() >= parent.getM()){
                points.clear();
                for(Point point2 : points1)
                    points.add(point2.getCopy());
                throw new TetrisException("Cant rotate counter clockwise!");
            } else if(!parent.isOK(point.getX(), point.getY(), id) || (n != 4 && !parent.isOK(point.getX(), point1.getY(), id))) {
                points.clear();
                for(Point point2 : points1)
                    points.add(point2.getCopy());
                throw new TetrisException("Cant rotate counter clockwise!");
            }
            point.minus(start);
        }
        parent.updateBoard();
    }

    public boolean isInBoard(int x, int y) {
        return x >= 0 && y >= 0 && y < parent.getM();
    }

    public void goLeft() throws Exception {
        for (Point point : getPointsInBoard()) {
            int xp = point.getX(), yp = point.getY() - 1;
            if (isInBoard(xp, yp)) {
                if (!parent.isOK(xp, yp, id)) {
                    throw new TetrisException("Cant Go left!");
                }
            } else {
                throw new TetrisException("Cant Go left!");
            }
        }
        start.sum(new Point(0, -1));
        parent.updateBoard();
    }

    public void goRight() throws Exception {
        for (Point point : getPointsInBoard()) {
            int xp = point.getX(), yp = point.getY() + 1;
            if (isInBoard(xp, yp)) {
                if (!parent.isOK(xp, yp, id)) {
                    throw new TetrisException("Cant Go right!");
                }
            } else {
                throw new TetrisException("Cant Go right!");
            }
        }
        start.sum(new Point(0, +1));
        parent.updateBoard();
    }

    public void goDown() throws Exception {
        for (Point point : getPointsInBoard()) {
            int xp = point.getX() - 1, yp = point.getY();
            if (isInBoard(xp, yp)) {
                if (!parent.isOK(xp, yp, id)) {
                    throw new TetrisException("Cant Go down!");
                }
            } else {
                throw new TetrisException("Cant Go down!");
            }
        }
        start.sum(new Point(-1, 0));
        parent.updateBoard();
    }

    public void deleteRow(int row) {
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            point.sum(start);
            if (point.getX() == row) {
                points.remove(i);
                i--;
                continue;
            } else if (point.getX() > row) {
                point.sum(new Point(-1, 0));
            }
            point.minus(start);
        }
    }
}
