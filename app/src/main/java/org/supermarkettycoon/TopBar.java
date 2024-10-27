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

    public TopBar(Globals globals, EventBus eventBus) {
        this.globals = globals;
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        layout.columnWeights = new double[]{1f, 1f, 1f, 1f};

        c.insets = new Insets(0, 20, 0, 20);

        setLayout(layout);

        c.fill = GridBagConstraints.BOTH;

        moneyLabel = new JLabel(String.format("Money %.2f$", globals.money));
        add(moneyLabel, c);

        dayLabel = new JLabel(String.format("Day %d (%s)", globals.day, globals.season()));
        add(dayLabel, c);

        energyLabel = new JLabel(String.format("Energy %d", globals.power));
        add(energyLabel, c);

        JButton nextDay = new JButton("Next Day");

        nextDay.addActionListener(e -> {
            globals.day++;
            globals.power = 10;
            for (TUpgrade upgrade : globals.upgrades) {
                if (upgrade.name.equals("energy_boost_1") ) {
                    globals.power += 5;   
                } else if (upgrade.name.equals("energy_boost_2")) {
                    globals.power += 10;
                } else if (upgrade.name.equals("energy_boost_3")) {
                    globals.power += 15;
                }
            }


            // Check if it's time to pay rent (every 10 days)
            if (globals.day % 10 == 0) {
                double rentAmount = 100.0;

                // Check if the player has enough money to pay rent
                if (globals.money >= rentAmount) {
                    globals.money -= rentAmount;
                    // Notify the player about the rent payment
                JOptionPane.showMessageDialog(this,
                        String.format("You paid $%.2f as rent.", rentAmount),
                        "Rent Payment",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Handle insufficient funds (e.g., Game Over)
                    JOptionPane.showMessageDialog(this,
                            "You don't have enough money to pay rent! Game Over.",
                            "Game Over",
                            JOptionPane.ERROR_MESSAGE);
                    // Implement game over logic here
                    // For example, disable the next day button or reset the game
                    nextDay.setEnabled(false);
                    return; // Exit the action listener to prevent further processing
                }
            }

            eventBus.post(new NewDayEvent());

            moneyLabel.setText(String.format("Money %.2f$", globals.money));
            dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
            energyLabel.setText(String.format("Energy %d", globals.power));

            RedrawSpriteEvent rse = new RedrawSpriteEvent();
            eventBus.post(rse);

            try {
                globals.saveGame(globals);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        add(nextDay, c);
    }

    @Subscribe
    public void updateTopBarOnGlobalUpdate(Globals globals) {
        moneyLabel.setText(String.format("Money %.2f$", globals.money));
        dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
        energyLabel.setText(String.format("Energy %d", globals.power));
    }
}