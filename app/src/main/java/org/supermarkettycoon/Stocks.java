package org.supermarkettycoon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class Stocks extends JPanel {
    public Stocks(Globals globals) {
        System.out.println(globals.day);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setBackground(Color.RED);

        // Set the layout of the JFrame
        setLayout(layout);

        TableModel tableModel = new DefaultTableModel() {

        };

        String column[] = {"Name", "Time Left", "Sell Price"};

        // Create a new JTable object
        JTable table = new JTable(data, column);


        c.weightx = c.weighty = 1;
        add(table, c);
    }

}
