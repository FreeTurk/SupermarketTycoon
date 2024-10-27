package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;
import javax.swing.*;
import java.awt.*;

class Main extends JFrame {

    public Main() {
        super("Supermarket Tycoon"); 

        // Creates and configures the layout for the main window
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.3f, 0.5f, 0.2f}; 
        layout.rowWeights = new double[]{0.0, 1.0}; 
        layout.rowHeights = new int[]{50, 0};

        // Initializes global settings and event bus for game state and communication
        Globals globals = new Globals();
        EventBus eventBus = EventBusFactory.getEventBus();

        // Creates and displays the Game Start dialog window
        GameStartDialog gameStartDialog = new GameStartDialog(this, globals);
        gameStartDialog.setVisible(true);

        // Sets the title with the username once the game starts
        setTitle(String.format("Supermarket Tycoon (%s)", globals.username));

        // Initializes various game panels and registers them with the event bus
        TopBar topBar = new TopBar(globals, eventBus);
        eventBus.register(topBar); 
        Stocks stocks = new Stocks(globals, eventBus);
        eventBus.register(stocks); 
        Upgrades upgrades = new Upgrades(globals, eventBus);
        eventBus.register(upgrades);

        // Initializes the main game screen and registers it with the event bus
        SpriteScreen spriteScreen = new SpriteScreen(globals);
        eventBus.register(spriteScreen);

        // Sets up the main window with specified settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(layout); 
        setSize(1920, 1200); 

        // Creates a GridBagConstraints object to control component positioning
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        // Adds the Stocks panel in the bottom-left corner
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(stocks, c);

        // Adds the TopBar panel across the top two columns
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(topBar, c);

        // Adds the SpriteScreen panel to the central area
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(spriteScreen, c);

        // Adds the Upgrades panel on the right side, spanning two rows
        c.gridheight = 2;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 0;
        add(upgrades, c);

        // Centers the main window on the screen and makes it visible
        setLocationRelativeTo(null);
        setVisible(true);

        // Checks if the player has gone bankrupt and shows LoseGameDialog if true
        if (globals.bankrupt) {
            new LoseGameDialog(this, globals).setVisible(true); 
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
