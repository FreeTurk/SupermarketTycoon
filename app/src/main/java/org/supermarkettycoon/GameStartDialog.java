package org.supermarkettycoon;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

public class GameStartDialog extends JDialog {
    public GameStartDialog(JFrame parent, Globals globals) {
        super(parent, "Game Start", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Makes it so that closing the JDialog will close the entire program instead of
        // just passing over to the game
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        System.exit(0);
                    }
                });

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        c.gridx = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        JLabel label = new JLabel("Welcome to Supermarket Tycoon!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label, c);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        newGameButton.addActionListener(e -> {
           JDialog newGameDialog = new NewGameDialog(this, globals);
        });

        add(newGameButton, c);

        JButton loadButton = new JButton("Load Game");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(loadButton, c);

    }
}

class NewGameDialog extends JDialog {
    public NewGameDialog(JDialog parent, Globals globals)  {
        super(parent, "New Game", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(layout);
        c.gridx = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        JLabel label = new JLabel("Username:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label, c);

        JTextField nameField = new JTextField(15);
        add(nameField, c);

        JButton createGameButton = new JButton("Create Game");

        createGameButton.addActionListener((e)  -> {
            try {
                globals.createGame(nameField.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        add(createGameButton, c);
        setVisible(true);
    }

}