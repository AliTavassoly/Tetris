package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScoreBoardPanel extends JPanel {
    JLabel nameLabel;
    ArrayList<Integer> scores;
    JLabel[] rows;
    GridBagConstraints grid;

    private MainFrame parent;

    public ScoreBoardPanel(MainFrame parent) {
        this.parent = parent;

        setBackground(new Color(0, 20, 41));

        nameLabel = new JLabel("scores list");
        scores = parent.getGame().getScores();
        grid = new GridBagConstraints();
        rows = new JLabel[10];

        for (int i = 0; i < 10; i++) {
            rows[i] = new JLabel("#" + i + " : ?");
            rows[i].setForeground(Color.white);
        }

        layoutComponent();
    }

    public void layoutComponent() {
        setLayout(new GridBagLayout());

        grid.gridy = 0;
        nameLabel.setForeground(Color.white);
        add(nameLabel, grid);

        for (int i = 0; i < 10; i++) {
            grid.gridy = i + 1;
            add(rows[i], grid);
        }
    }

    public void update() {
        scores = parent.getGame().getScores();
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            if (i < 9)
                rows[i].setText("#" + i + " : " + scores.get(scores.size() - i - 1));
            rows[i].setForeground(Color.blue);
        }
    }
}
