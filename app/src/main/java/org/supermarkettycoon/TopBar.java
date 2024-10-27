package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.swing.*;
import java.awt.*;

public class TopBar extends JPanel {
    JLabel moneyLabel; 
    JLabel dayLabel; 
    JLabel energyLabel; 
    Globals globals; 

    // Constructor to initialize the TopBar with global settings and event bus
    public TopBar(Globals globals, EventBus eventBus) {
        this.globals = globals;
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        layout.columnWeights = new double[]{1f, 1f, 1f, 1f}; 

        c.insets = new Insets(0, 20, 0, 20); 
        setLayout(layout);

        c.fill = GridBagConstraints.BOTH; 

        // Initializes and adds the money label to display the player's money
        moneyLabel = new JLabel(String.format("Money %.2f$", globals.money));
        add(moneyLabel, c);

        // Initializes and adds the day label to display the current day and season
        dayLabel = new JLabel(String.format("Day %d (%s)", globals.day, globals.season()));
        add(dayLabel, c);

        // Initializes and adds the energy label to display the player's energy
        energyLabel = new JLabel(String.format("Energy %d", globals.power));
        add(energyLabel, c);

        // Button to progress to the next day
        JButton nextDay = new JButton("Next Day");

        // Action listener to update game state and handle daily changes when the button is clicked
        nextDay.addActionListener(e -> {
            globals.day++; 
            globals.power = 10; 

            // Adjusts power based on upgrades
            for (TUpgrade upgrade : globals.upgrades) {
                if (upgrade.name.equals("energy_boost_1")) {
                    globals.power += 5;
                } else if (upgrade.name.equals("energy_boost_2")) {
                    globals.power += 10;
                } else if (upgrade.name.equals("energy_boost_3")) {
                    globals.power += 15;
                }
            }

            // Checks if it’s time to pay rent (every 10 days)
            if (globals.day % 10 == 0) {
                double rentAmount = 100.0;

                // If the player has enough money to pay rent, deduct the rent amount
                if (globals.money >= rentAmount) {
                    globals.money -= rentAmount;
                    JOptionPane.showMessageDialog(this,
                            String.format("You paid $%.2f as rent.", rentAmount),
                            "Rent Payment",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // If funds are insufficient, display a Game Over message and disable the next day button
                    globals.bankrupt = true;
                    nextDay.setEnabled(false); 
                    return; 
                }
            }

            eventBus.post(new NewDayEvent());

            // Updates labels with the latest game state information
            moneyLabel.setText(String.format("Money %.2f$", globals.money));
            dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
            energyLabel.setText(String.format("Energy %d", globals.power));

            // Posts an event to update other components (e.g., sprites)
            RedrawSpriteEvent rse = new RedrawSpriteEvent();
            eventBus.post(rse);

            // Saves the game state to file
            try {
                globals.saveGame(globals);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        add(nextDay, c); 
    }

    // Method to update the top bar labels when the global state changes
    @Subscribe
    public void updateTopBarOnGlobalUpdate(Globals globals) {
        moneyLabel.setText(String.format("Money %.2f$", globals.money));
        dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
        energyLabel.setText(String.format("Energy %d", globals.power));
    }
}
