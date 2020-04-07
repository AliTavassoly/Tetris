package gui;

import logic.Piece;
import logic.Point;

import javax.swing.*;
import java.awt.*;

public class NextPiecePanel extends JPanel {
    private MainFrame parent;

    public NextPiecePanel(MainFrame parent) {
        this.parent = parent;
    }

    @Override
    protected void paintComponent(Graphics g) {
        var cellWidth = getPreferredSize().width / 4;
        var cellHeight = getPreferredSize().height / 4;

        Piece piece = parent.getGame().getNextPiece();

        g.clearRect(0, 0, getPreferredSize().width, getPreferredSize().height);

        if (piece == null)
            return;

        for (Point point : piece.getPoints()) {
            int i = point.getX();
            int j = point.getY();

            g.setColor(piece.getColor());
            g.fillRect(j * cellWidth + 1, (4 - i - 1) * cellHeight + 1, cellWidth - 2, cellHeight - 2);
        }
    }
}
