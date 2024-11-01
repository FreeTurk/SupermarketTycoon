package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

public class Stocks extends JPanel {
    JTable table; 
    DefaultTableModel model; 
    String[] columns = {"Amount", "Name", "Time Left", "Sell Price"}; 
    Globals globals; 
    EventBus eventBus; 

    // Constructor to initialize the Stocks panel with global settings and event bus
    public Stocks(Globals _globals, EventBus _eventBus) {
        eventBus = _eventBus;
        globals = _globals;
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        setBackground(Color.RED); 

        // Sets layout for the panel
        setLayout(layout);

        // Creates the table model with custom behavior for cell editing
        Globals finalGlobals = globals;
        this.model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; 
            }

            DecimalFormat formatter = new DecimalFormat("0.00"); 

            // Overrides the setValueAt method to handle sell price changes
            @Override
            public void setValueAt(Object newValue, int row, int column) {
                Double oldPrice = (Double) getValueAt(row, column);
                Integer amount = (Integer) getValueAt(row, 0);
                String fullname = (String) getValueAt(row, 1);
                Integer timeLeft = (Integer) getValueAt(row, 2);
                double newPriceFormatted = Double.parseDouble(formatter.format(Double.valueOf((String) newValue)));

                // Updates the sell price in the Globals object if it matches the product in the table
                for (int i = 0; i < finalGlobals.products.size(); i++) {
                    TBoughtProducts product = finalGlobals.products.get(i);
                    Integer expiryDate = timeLeft + (finalGlobals.day - product.buydate);

                    if (oldPrice.equals(product.price) &&
                            product.fullname.equals(fullname) &&
                            amount.equals(product.quantity) &&
                            expiryDate.equals(product.expiry_time)) {
                        finalGlobals.products.get(i).price = Double.parseDouble((String) newValue);
                    }
                }

                super.setValueAt(Double.toString(newPriceFormatted), row, column); 
            }
        };

        updateTable(globals); 

        JScrollPane scrollPane = new JScrollPane();
        this.table = new JTable(this.model); 
        this.table.setRowHeight(30); 
        this.table.getTableHeader().setReorderingAllowed(false); 
        this.table.setAlignmentX(Component.CENTER_ALIGNMENT); 

        // Renderer to center text in cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer); 

        // Adds table to the scroll pane and places it in the layout
        c.weightx = c.weighty = 1;
        add(scrollPane, c);
        scrollPane.setViewportView(table);
    }

    // Method to populate table with data from Globals
    public void updateTable(Globals globals) {
        Object[][] rows = new Object[globals.products.size()][4]; 

        // Populates rows with product data
        for (int i = 0; i < globals.products.size(); i++) {
            rows[i][0] = globals.products.get(i).quantity;
            rows[i][1] = globals.products.get(i).fullname;
            rows[i][2] = globals.products.get(i).expiry_time - (globals.day - globals.products.get(i).buydate);
            rows[i][3] = globals.products.get(i).price;
        }

        this.model.setDataVector(rows, columns); 
    }

    // Event listener to update the table when global data changes
    @Subscribe
    public void updateTableOnGlobalChange(Globals globals) {
        updateTable(globals); 
        this.model.fireTableDataChanged(); 
    }

    // Event listener to handle daily product sales updates
    @Subscribe
    public void dailyProductSellUpdate(NewDayEvent nde) {
        SwingUtilities.invokeLater(() -> {
            int totalCustomers = 0; 

            for (Iterator<TBoughtProducts> iterator = globals.products.iterator(); iterator.hasNext(); ) {
                TBoughtProducts product = iterator.next();
                Random random = new Random();

                int leftDayForProd = product.expiry_time - (globals.day - product.buydate); 

                // Removes expired products from the list
                if (leftDayForProd <= 0) {
                    iterator.remove();
                    continue;
                }

                // Calculates the maximum possible customers based on product quantity and price ratio
                int maxCustomers = (int) (Math.round(((double) product.quantity/2))
                        * (product.originalPrice / product.price));

                // Determines a random sell amount based on maxCustomers
                int sellAmount = maxCustomers > 0 ? random.nextInt(1, maxCustomers + 1) : 0;

                // Ensures sell amount doesn't exceed available stock
                if (sellAmount >= product.quantity) {
                    sellAmount = product.quantity;
                }

                globals.money += product.price * sellAmount; 
                product.quantity -= sellAmount; 
                totalCustomers += sellAmount; 

                // Removes products with zero quantity after sales
                if (product.quantity <= 0) {
                    iterator.remove();
                }
            }

            updateTable(globals); 
            this.model.fireTableDataChanged(); 

            // Posts an event to update customer number in the UI
            RedrawSpriteEvent rse = new RedrawSpriteEvent();
            rse.customerNumber = totalCustomers;
            eventBus.post(rse);
        });
    }
}
