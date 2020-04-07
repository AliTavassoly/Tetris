package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameDialog extends JDialog {
    private JLabel nameLabel;
    private JTextField nameField;
    private JButton okButton;

    MainFrame parent;

    public NameDialog(MainFrame parent) {
        setSize(300, 300);
        getContentPane().setBackground(new Color(0, 20, 41));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - this.getWidth()) / 2;
        int y = (screenSize.height - this.getHeight()) / 2;
        this.setLocation(x, y);

        this.parent = parent;

        setModal(true);

        nameLabel = new JLabel("Name : ");
        nameField = new JTextField(10);
        okButton = new JButton("ok");

        nameLabel.setForeground(Color.white);

        layoutComponent();


        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setVisible(false);
                    dispose();
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                dispose();
            }
        });

        setResizable(false);

    }

    public void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridy = 0;

        grid.weightx = 2;
        grid.weighty = 0.1;

        grid.gridx = 0;
        grid.anchor = GridBagConstraints.LINE_END;
        add(nameLabel, grid);

        grid.gridx = 1;
        grid.anchor = GridBagConstraints.LINE_START;
        add(nameField, grid);

        // second row
        grid.gridy++;

        grid.weightx = 1;
        grid.weighty = 0.1;

        grid.gridx = 0;
        grid.gridwidth = 2;
        grid.anchor = GridBagConstraints.CENTER;
        add(okButton, grid);
    }

    public String getValue() {
        setVisible(true);
        return nameField.getText();
    }
}