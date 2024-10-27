package org.supermarkettycoon;

import javax.swing.*;

import java.awt.*;
import java.util.Random;

import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.EventBus;

public class TopBar extends JPanel {
    JLabel moneyLabel;
    JLabel dayLabel;
    JLabel energyLabel;
    Globals globals;


    TopBar(Globals _globals, EventBus eventBus) {
        this.globals = _globals;
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

            
            eventBus.post(new NewDayEvent());
            eventBus.post(globals);

            try {
                globals.saveGame(globals);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        add(nextDay, c);

    }


    @Subscribe
    public void updateTopBarOnGlobalUpdate(NewDayEvent nde) {
        moneyLabel.setText(String.format("Money %.2f$", globals.money));
        dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
        energyLabel.setText(String.format("Energy %d", globals.power));
    }


}
