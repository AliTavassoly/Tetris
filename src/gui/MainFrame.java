package gui;

import logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Collections;

import javax.sound.sampled.*;

public class MainFrame extends JFrame implements KeyListener, ActionListener {
    private BoardPanel boardPanel;
    private NextPiecePanel nextPiecePanel;
    private ScoreBoardPanel scoreBoardPanel;
    private JLabel nameLabel;
    private JLabel scoreLabel;
    private String name;

    private Game game;

    public MainFrame() {
        setSize(800, 700);
        getContentPane().setBackground(new Color(0, 20, 41));
        setBackground(new Color(0, 20, 41));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;

        this.setLocation(x, y);

        nameLabel = new JLabel("name : ");
        scoreLabel = new JLabel("score : 0 (" + 0 + " lines)");

        NameDialog nameDialog = new NameDialog(this);
        name = nameDialog.getValue();
        nameLabel.setText("name : " + name);
        newGame();

        boardPanel = new BoardPanel(this, game.getN(), game.getM());
        nextPiecePanel = new NextPiecePanel(this);
        scoreBoardPanel = new ScoreBoardPanel(this);

        boardPanel.setPreferredSize(new Dimension(400, 640));
        nextPiecePanel.setPreferredSize(new Dimension(200, 200));
        scoreBoardPanel.setPreferredSize(new Dimension(200, 200));

        nameLabel.setForeground(Color.white);
        scoreLabel.setForeground(Color.white);

        playBackground();

        layoutComponent();

        addKeyListener(this);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void newGame() {
        if (game != null) {
            game.getScores().add(game.getScore());
            Collections.sort(game.getScores());
            scoreBoardPanel.update();
        }
        game = new Game(this, 16, 10);
    }

    public void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        //first col
        grid.gridx = 0;
        grid.gridy = 0;

        grid.weightx = 20;
        grid.weighty = 0.1;

        grid.gridheight = 4;
        add(boardPanel, grid);

        // second col
        grid.gridx = 1;

        grid.gridy = 0;
        grid.gridheight = 1;
        add(nameLabel, grid);

        grid.gridy = 1;
        grid.gridheight = 1;
        add(scoreLabel, grid);

        grid.gridy = 2;
        grid.gridheight = 1;
        add(nextPiecePanel, grid);

        grid.gridy = 3;
        grid.gridheight = 1;
        add(scoreBoardPanel, grid);
    }

    public Game getGame() {
        return game;
    }

    public void repaintBoardPanel() {
        boardPanel.repaint();
    }

    public void repaintNextPiecePanel() {
        nextPiecePanel.repaint();
    }

    public void updateScoreLabel() {
        scoreLabel.setText("score : " + getGame().getScore() + " (" + getGame().getDeletedRows() + " lines)");
    }

    public void playError() {
        try {
            File file = new File("./sounds/error.wav");
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
        }
    }

    public void playGameOver() {
        try {
            File file = new File("./sounds/game_over.wav");
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
        }
    }

    public void playBackground() {
        try {
            File file = new File("./sounds/background.wav");
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        try {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    getGame().getCurrentPiece().goRight();
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    getGame().getCurrentPiece().goLeft();
                    break;
                case KeyEvent.VK_E:
                    getGame().getCurrentPiece().rotateClockwise();
                    break;
                case KeyEvent.VK_Q:
                    getGame().getCurrentPiece().rotateCounterClockwise();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    while (true) {
                        try {
                            getGame().getCurrentPiece().goDown();
                        } catch (Exception e) {
                            break;
                        }
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    getGame().repent();
                    break;
                default:
                    playError();
            }
        } catch (Exception e) {
            playError();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}