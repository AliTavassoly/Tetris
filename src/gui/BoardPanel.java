package gui;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private  int width, height;
    private MainFrame parent;

    public BoardPanel(MainFrame parent, int height, int width){
        this.parent = parent;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        var cellWidth = getPreferredSize().width / width;
        var cellHeight = getPreferredSize().height / height;

        int [][] board =  parent.getGame().getBoard();

        for (int i = 0; i < height; i++){
            for (int j = 0; j <  width; j++){
                if(board[i][j] == -1){
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(parent.getGame().getPieces().get(board[i][j]).getColor());
                }
                g.fillRect(j * cellWidth + 1, (height - i - 1) * cellHeight + 1, cellWidth - 2, cellHeight - 2);
            }
        }
    }
}
