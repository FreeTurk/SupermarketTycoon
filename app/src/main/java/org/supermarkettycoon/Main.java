package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

class Main extends JFrame {

    public Main() {
        super("Supermarket Tycoon");

        // Creates the layout for the window
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.3f, 0.5f, 0.2f};
        layout.rowWeights = new double[]{0.0, 1.0};
        layout.rowHeights = new int[]{50, 0};

        Globals globals = new Globals();
        EventBus eventBus = EventBusFactory.getEventBus();


        GameStartDialog gameStartDialog = new GameStartDialog(this, globals);

        gameStartDialog.setVisible(true);


        setTitle(String.format("Supermarket Tycoon (%s)", globals.username));

        TopBar topBar = new TopBar(globals, eventBus);
        eventBus.register(topBar);
        Stocks stocks = new Stocks(globals, eventBus);
        eventBus.register(stocks);
        Upgrades upgrades = new Upgrades(globals, eventBus);
        eventBus.register(upgrades);


<<<<<<< Updated upstream
<<<<<<< Updated upstream
        SpriteScreen spriteScreen = new SpriteScreen();
=======
        SpriteScreen spriteScreen = new SpriteScreen(globals);
>>>>>>> Stashed changes
=======
        SpriteScreen spriteScreen = new SpriteScreen(globals);
<<<<<<< HEAD
        eventBus.register(spriteScreen);
<<<<<<< HEAD
=======
>>>>>>> origin/frontend
=======
>>>>>>> Stashed changes
>>>>>>> parent of 5571b23 (Trying to fix shit)
        GridBagConstraints c = new GridBagConstraints();

        // Sets the layout of the main window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(layout);
        setSize(1920, 1200);

        c.fill = GridBagConstraints.BOTH;

        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(stocks, c);

        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        add(topBar, c);

        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 1;
        add(spriteScreen, c);

        c.gridheight = 2;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 0;
        add(upgrades, c);

        // Adds the sidebar to the main window

        // Make the window visible
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new Main();
    }

}