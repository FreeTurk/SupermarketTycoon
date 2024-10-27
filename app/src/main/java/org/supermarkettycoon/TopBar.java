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


    TopBar(Globals globals, EventBus eventBus) {
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

            int totalCustomers = 0;

            int maxIndex = globals.products.size();


            for (int i = 0; i < maxIndex; i++) {
                TBoughtProducts product = globals.products.get(i);
                Random random = new Random();

                int maxCustomers = (int) Math.round(((double) globals.day / 10) * (product.originalPrice / product.price));
                int sellAmount = random.nextInt(0, maxCustomers + 1);

                globals.money += product.price * sellAmount;

                if (sellAmount >= product.quantity) {
                    sellAmount = product.quantity;
                }

                globals.products.get(i).quantity -= sellAmount;
                totalCustomers += sellAmount;
                sellAmount = 0;
            }


            eventBus.post(new NewDayEvent());


            moneyLabel.setText(String.format("Money %.2f$", globals.money));
            dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
            energyLabel.setText(String.format("Energy %d", globals.power));

            RedrawSpriteEvent rse = new RedrawSpriteEvent();
            rse.customerNumber = totalCustomers;
            eventBus.post(rse);

            rse = new RedrawSpriteEvent();
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
