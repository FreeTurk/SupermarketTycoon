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

        // Sets a listener that exits the program when the dialog is closed
        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        System.exit(0); 
                    }
                });

        // Sets layout for the dialog
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        // Positions components centrally with insets and full width
        c.gridx = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        // Adds a label to display "You Lose!" message
        JLabel label = new JLabel("You Lose!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label, c);

        // Adds a label to inform the player that their supermarket has gone bankrupt
        JLabel label2 = new JLabel("Your supermarket has gone bankrupt");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label2, c);
    }
}
