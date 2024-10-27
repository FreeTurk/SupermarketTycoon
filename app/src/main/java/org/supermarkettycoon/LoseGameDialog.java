package org.supermarkettycoon;

import com.google.gson.Gson;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class LoseGameDialog extends JDialog {
    public LoseGameDialog(JFrame parent, Globals globals) {
        super(parent, "Game Lost", true);
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

        JLabel label = new JLabel("You Lose!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label, c);

        JLabel label2 = new JLabel("Your supermarket has gone bankrupt");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentY(Component.CENTER_ALIGNMENT);

        add(label2, c);
    }
}