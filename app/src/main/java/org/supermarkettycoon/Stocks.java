package org.supermarkettycoon;

import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class Stocks extends JPanel {
    JTable table;
    DefaultTableModel model;


    public Stocks(Globals globals) {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        setBackground(Color.RED);

        // Set the layout of the JFrame
        setLayout(layout);


        String[] columns = {"Amount", "Name", "Time Left", "Sell Price"};


        Object[][] rows = new Object[globals.products.size()][4];

        for (int i = 0; i < globals.products.size(); i++) {
            rows[i][0] = globals.products.get(i).quantity;
            rows[i][1] = globals.products.get(i).name;
            rows[i][2] = globals.products.get(i).expiry_time - (globals.day - globals.products.get(i).buydate);
            rows[i][3] = globals.products.get(i).price;
        }

        this.model = new DefaultTableModel(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        JScrollPane scrollPane = new JScrollPane();
        // Create a new JTable object
        this.table = new JTable(this.model);
        this.table.setRowHeight(30);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Class to center text inside the cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        c.weightx = c.weighty = 1;
        add(scrollPane, c);
        scrollPane.setViewportView(table);
    }

    @Subscribe
    public void updateTableOnGlobalChange(Globals globals) {
        String[] columns = {"Amount", "Name", "Time Left", "Sell Price"};

        Object[][] rows = new Object[globals.products.size()][4];

        for (int i = 0; i < globals.products.size(); i++) {
            rows[i][0] = globals.products.get(i).quantity;
            rows[i][1] = globals.products.get(i).name;
            rows[i][2] = globals.products.get(i).expiry_time - (globals.day - globals.products.get(i).buydate);
            rows[i][3] = globals.products.get(i).price;
        }

        this.model.setDataVector(rows, columns);

        this.model.fireTableDataChanged();
    }

}
