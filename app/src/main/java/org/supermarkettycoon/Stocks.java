package org.supermarkettycoon;

import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;

public class Stocks extends JPanel {
    JTable table;
    DefaultTableModel model;
    String[] columns = {"Amount", "Name", "Time Left", "Sell Price"};


    public Stocks(Globals globals) {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;

        setBackground(Color.RED);

        // Set the layout of the JFrame
        setLayout(layout);


        this.model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }

            DecimalFormat formatter = new DecimalFormat("0.00");


            @Override
            public void setValueAt(Object newValue, int row, int column) {
                Double oldPrice = (Double) getValueAt(row, column);
                Integer amount = (Integer) getValueAt(row, 0);
                String name = (String) getValueAt(row, 1);
                Integer timeLeft = (Integer) getValueAt(row, 2);
                double newPriceFormatted = Double.parseDouble(formatter.format(Double.valueOf((String) newValue)));


                for (int i = 0; i < globals.products.size(); i++) {
                    TBoughtProducts product = globals.products.get(i);
                    Integer expiryDate = timeLeft + (globals.day - product.buydate);

                    if (oldPrice.equals(product.price) &&
                            product.name.equals(name) &&
                            amount.equals(product.quantity) &&
                            expiryDate.equals(product.expiry_time)) {
                        globals.products.get(i).price = Double.parseDouble((String) newValue);
                    }
                }

                super.setValueAt(Double.toString(newPriceFormatted), row, column);
            }
        };

        updateTable(globals);

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

    public void updateTable(Globals globals) {
        Object[][] rows = new Object[globals.products.size()][4];

        for (int i = 0; i < globals.products.size(); i++) {
            rows[i][0] = globals.products.get(i).quantity;
            rows[i][1] = globals.products.get(i).name;
            rows[i][2] = globals.products.get(i).expiry_time - (globals.day - globals.products.get(i).buydate);
            rows[i][3] = globals.products.get(i).price;
        }

        this.model.setDataVector(rows, columns);
    }

    @Subscribe
    public void updateTableOnGlobalChange(Globals globals) {
        String[] columns = {"Amount", "Name", "Time Left", "Sell Price"};

        updateTable(globals);

        this.model.fireTableDataChanged();
    }

}
