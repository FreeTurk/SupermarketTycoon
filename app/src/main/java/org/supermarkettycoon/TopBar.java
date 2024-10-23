package org.supermarkettycoon;

import javax.swing.*;

import java.awt.*;

public class TopBar extends JPanel {
  TopBar(Globals globals) {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    layout.columnWeights = new double[] { 1f, 1f, 1f, 1f };

    c.insets = new Insets(0, 20, 0, 20);

    setLayout(layout);

    c.fill = GridBagConstraints.BOTH;

    JLabel moneyLabel = new JLabel(String.format("Money %.2f$", globals.money));

    add(moneyLabel, c);

    JLabel dayLabel = new JLabel(String.format("Day %d (%s)", globals.day, globals.season()));

    add(dayLabel, c);

    JLabel energyLabel = new JLabel(String.format("Energy %d", globals.power));

    add(energyLabel, c);

    JButton nextDay = new JButton("Next Day");

    nextDay.addActionListener(e -> {
      globals.day++;
      dayLabel.setText(String.format("Day %d (%s)", globals.day, globals.season()));
    });

    add(nextDay, c);
  }
}
