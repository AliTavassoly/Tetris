package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverDialog extends JDialog {
    private JLabel messageLabel;
    private JButton noButton;
    private JButton yesButton;

    private MainFrame parent;

    public GameOverDialog(MainFrame parent){
        setSize(400, 300);
        getContentPane().setBackground(new Color(0, 20, 41));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);


        this.parent = parent;

        setModal(true);

        messageLabel = new JLabel("Game ended. Do you want to play again?");
        noButton = new JButton("no");
        yesButton = new JButton("yes");

        messageLabel.setForeground(Color.white);

        layoutComponent();

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                parent.newGame();
                dispose();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                System.exit(0);
                dispose();
            }
        });
        setResizable(false);
        setVisible(true);
    }

    public void layoutComponent(){
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridy = 0;

        grid.weighty = 1;
        grid.weightx = 10;

        grid.gridx = 0;
        grid.gridwidth = 2;
        add(messageLabel, grid);

        // second row
        grid.gridy = 1;

        grid.gridx = 0;
        grid.gridwidth = 1;
        add(noButton, grid);

        grid.gridx = 1;
        grid.gridwidth = 1;
        add(yesButton, grid);
    }
}
