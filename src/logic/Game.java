package logic;

import gui.GameOverDialog;
import gui.MainFrame;

import java.awt.*;
import java.util.*;

public class Game {
    private int[][] board = new int[50][50];
    private int n, m;
    public static ArrayList<Integer> scores = new ArrayList<>();
    public ArrayList<Integer> piecesId = new ArrayList<>();
    private int score, numberOfPiece;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private Piece currentPiece;
    private int currentPieceId;
    private int deletedRows;

    private Timer timer;
    private TimerTask task;

    boolean isPause;

    private MainFrame parent;

    public Game(MainFrame parent, int n, int m) {
        this.parent = parent;
        this.n = n;
        this.m = m;
        score = 0;
        numberOfPiece = 0;
        deletedRows = 0;

        for (int i = 0; i < 1000; i++) {
            Random random = new Random();
            piecesId.add(random.nextInt(7));
        }

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                board[i][j] = -1;
            }
        }

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                try {
                    currentPiece.goDown();
                    updateBoard();
                } catch (Exception e) {
                    if(currentPiece != null) {
                        for (Point point : currentPiece.getPointsInBoard()) {
                            if (point.getX() >= n) {
                                gameEnded();
                                timer.cancel();
                                timer.purge();
                            }
                        }
                    }
                    if (numberOfPiece > 0)
                        changeScore(1);
                    removeFullRows();
                    dropPiece(false);
                    parent.repaintNextPiecePanel();
                    updateBoard();
                    try {
                        currentPiece.goDown();
                    } catch (Exception gameEnded) {
                        gameEnded();
                        timer.cancel();
                        timer.purge();
                    }
                    updateBoard();
                }
            }
        };
        long delay = 2000L;
        long period = 1000L;
        timer.schedule(task, delay, period);
    }

    public void gameEnded() {
        parent.playGameOver();
        GameOverDialog gameOverDialog = new GameOverDialog(parent);
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void changeScore(int change) {
        score += change;
        score = Math.max(score, 0);
        parent.updateScoreLabel();
    }

    public void repent() {
        changeScore(-1);
        dropPiece(true);
    }

    public Piece getPieceById(int pieceId) {
        switch (pieceId) {
            case 0: // choob
                return new Piece(this, numberOfPiece, 4, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3))), new Point(n, m / 2 - 1), new Color(218, 1, 0));
            case 1: // paie chap
                return new Piece(this, numberOfPiece, 3, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 1), new Point(0, 2), new Point(1, 2), new Point(2, 2))), new Point(n, m / 2 - 1), new Color(24, 21, 203));
            case 2: // paie rast
                return new Piece(this, numberOfPiece, 3, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 1), new Point(0, 0), new Point(1, 0), new Point(2, 0))), new Point(n, m / 2 - 1), new Color(255, 21, 203));
            case 3: // panjere
                return new Piece(this, numberOfPiece, 2, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1))), new Point(n, m / 2 - 1), new Color(255, 221, 0));
            case 4: // kooh
                return new Piece(this, numberOfPiece, 3, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 1))), new Point(n, m / 2 - 1), new Color(148, 68, 0));
            case 5: //ordak rast
                return new Piece(this, numberOfPiece, 3, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 0), new Point(0, 1), new Point(1, 2), new Point(1, 1))), new Point(n, m / 2 - 1), new Color(0, 255, 0));
            case 6: // ordake chap
                return new Piece(this, numberOfPiece, 3, new ArrayList<Point>(
                        Arrays.asList(new Point(0, 1), new Point(0, 2), new Point(1, 0), new Point(1, 1))), new Point(n, m / 2 - 1), new Color(0, 150, 0));
        }
        return null;
    }

    public void dropPiece(boolean isRepent) {
        Random random = new Random();
        if (!isRepent) {
            //currentPieceId = random.nextInt(7);
            currentPieceId = piecesId.get(numberOfPiece);
        } else {
            pieces.remove(pieces.size() - 1);
        }
        if (isRepent)
            numberOfPiece--;
        currentPiece = getPieceById(currentPieceId);
        pieces.add(currentPiece);
        numberOfPiece++;
    }

    public void removeFullRows() {
        for (int i = 0; i < n; i++) {
            boolean isFull = true;
            for (int j = 0; j < m; j++) {
                if (board[i][j] == -1) {
                    isFull = false;
                }
            }
            if (isFull) {
                for (Piece piece : pieces) {
                    piece.deleteRow(i);
                }
                changeScore(10);
                deletedRows++;
                updateBoard();
                parent.updateScoreLabel();
                i--;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getNextPiece() {
        return getPieceById(piecesId.get(numberOfPiece));
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void updateBoard() {
        for (int i = 0; i < 50; i++)
            for (int j = 0; j < 50; j++)
                board[i][j] = -1;
        for (Piece piece : pieces) {
            for (Point point : piece.getPointsInBoard()) {
                board[point.getX()][point.getY()] = piece.getId();
            }
        }
        parent.repaintBoardPanel();
    }

    public boolean isOK(int x, int y, int id) {
        return (board[x][y] == -1 || board[x][y] == id);
    }

    public int getDeletedRows() {
        return deletedRows;
    }
}
