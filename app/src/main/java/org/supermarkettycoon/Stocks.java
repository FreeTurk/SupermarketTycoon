package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;

public class Stocks extends JPanel {
  public Stocks() {
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    setBackground(Color.RED);

    // Set the layout of the JFrame
    setLayout(layout);
    String data[][] = { { "101", "Amit", "670000" },
        { "102", "Jai", "780000" },
        { "101", "Sachin", "700000" } };

    String column[] = { "ID", "NAME", "SALARY" };

    // Create a new JTable object
    JTable table = new JTable(data, column);

    c.weightx = c.weighty = 1;
    add(table, c);
  }

}
