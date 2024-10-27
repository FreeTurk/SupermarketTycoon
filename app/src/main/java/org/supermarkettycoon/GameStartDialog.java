package org.supermarkettycoon;

import com.google.gson.Gson;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class GameStartDialog extends JDialog {
    
    public GameStartDialog(JFrame parent, Globals globals) {
        super(parent, "Game Start", true);
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

        // Adds a welcome label
        JLabel label = new JLabel("Welcome to Supermarket Tycoon!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(label, c);

        // Creates a button to start a new game
        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Action listener opens the NewGameDialog when clicked
        newGameButton.addActionListener(e -> {
            JDialog newGameDialog = new NewGameDialog(this, globals);
        });
        add(newGameButton, c);

        // Creates a button to load an existing game
        JButton loadButton = new JButton("Load Game");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Action listener opens the LoadGameDialog when clicked
        loadButton.addActionListener(e -> {
            JDialog loadGameDialog = new LoadGameDialog(this, globals);
        });
        add(loadButton, c);
    }
}

// Dialog window for creating a new game
class NewGameDialog extends JDialog {
    public NewGameDialog(JDialog parent, Globals globals) {
        super(parent, "New Game", true); 
        setSize(800, 600); 
        setLocationRelativeTo(parent); 

        // Sets layout for dialog
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        // Positions components centrally with insets and full width
        c.gridx = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;

        // Adds a label for username input
        JLabel label = new JLabel("Username:");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label, c);

        // Adds a text field for username entry
        JTextField nameField = new JTextField(15);
        add(nameField, c);

        // Adds a button to create a new game with the provided username
        JButton createGameButton = new JButton("Create Game");

        // Action listener calls createGame method on button click
        createGameButton.addActionListener((e) -> {
            try {
                globals.createGame(nameField.getText(), globals);
                dispose(); 
                parent.dispose();
            } catch (Exception ex) {
                throw new RuntimeException(ex); 
            }
        });

        add(createGameButton, c);
        setVisible(true); 
    }
}

// Dialog window for loading a saved game
class LoadGameDialog extends JDialog {
    LoadGameDialog(JDialog parent, Globals globals) {
        super(parent, "Load Game", true); 
        setSize(800, 600); 
        setLocationRelativeTo(parent); 

        // Sets layout for dialog
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(layout);

        // Positions components centrally with insets and full width
        c.gridx = 1;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);

        // Gets the directory for saved game files
        File saveFileDir = new File(System.getProperty("user.home") + "/.smtycoon");
        File[] allSaveFiles = saveFileDir.listFiles(); 

        // Creates a button for each save file to load the respective game
        for (File saveFile : allSaveFiles) {
            JButton loadButton = new JButton(saveFile.getName().split(".json")[0]); 

            // Action listener loads the selected game file on button click
            loadButton.addActionListener(e -> {
                try {
                    globals.loadGame(globals, saveFile); 
                } catch (Exception ex) {
                    throw new RuntimeException(ex); 
                }

                dispose();
                parent.dispose(); 
            });

            add(loadButton, c); 
        }

        setVisible(true);
    }
}
