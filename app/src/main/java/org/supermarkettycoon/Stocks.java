package org.supermarkettycoon;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Random;

public class Stocks extends JPanel {
    JTable table;
    DefaultTableModel model;
    String[] columns = {"Amount", "Name", "Time Left", "Sell Price"};
<<<<<<< HEAD
    Globals _globals;
    EventBus _eventBus;


    public Stocks(Globals globals, EventBus eventBus) {
        _globals = globals;
        _eventBus = eventBus;
=======
<<<<<<< Updated upstream
    Globals _globals;


    public Stocks(Globals globals) {
        _globals = globals;
=======
    Globals globals;


    public Stocks(Globals globals) {
        globals = globals;
>>>>>>> Stashed changes
>>>>>>> origin/frontend
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


<<<<<<< Updated upstream
                for (int i = 0; i < _globals.products.size(); i++) {
                    TBoughtProducts product = _globals.products.get(i);
                    Integer expiryDate = timeLeft + (_globals.day - product.buydate);
=======
                for (int i = 0; i < globals.products.size(); i++) {
                    TBoughtProducts product = globals.products.get(i);
                    Integer expiryDate = timeLeft + (globals.day - product.buydate);
>>>>>>> Stashed changes

                    if (oldPrice.equals(product.price) &&
                            product.name.equals(name) &&
                            amount.equals(product.quantity) &&
                            expiryDate.equals(product.expiry_time)) {
<<<<<<< Updated upstream
                        _globals.products.get(i).price = Double.parseDouble((String) newValue);
=======
                        globals.products.get(i).price = Double.parseDouble((String) newValue);
>>>>>>> Stashed changes
                    }
                }

                super.setValueAt(newPriceFormatted, row, column);
            }
        };

        Object[][] rows = new Object[globals.products.size()][4];

        for (int i = 0; i < globals.products.size(); i++) {
            rows[i][0] = globals.products.get(i).quantity;
            rows[i][1] = globals.products.get(i).name;
            rows[i][2] = globals.products.get(i).expiry_time - (globals.day - globals.products.get(i).buydate);
            rows[i][3] = globals.products.get(i).price;
        }

        this.model.setDataVector(rows, columns);

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
    public void updateTable(RedrawTableEvent globals) {
        Object[][] rows;

        if (!globals.globals.products.isEmpty()) {
            rows = new Object[globals.globals.products.size()][4];

            for (int i = 0; i < globals.globals.products.size(); i++) {
                rows[i][0] = globals.globals.products.get(i).quantity;
                rows[i][1] = globals.globals.products.get(i).name;
                rows[i][2] = globals.globals.products.get(i).expiry_time - (globals.globals.day - globals.globals.products.get(i).buydate);
                rows[i][3] = globals.globals.products.get(i).price;
            }

        } else {
            rows = new Object[0][4];
        }


        this.model.setDataVector(rows, columns);
        this.model.fireTableDataChanged();

        _eventBus.post(globals.globals);
    }

<<<<<<< HEAD
=======
    @Subscribe
    public void dailyProductSellUpdate(NewDayEvent nde) {
<<<<<<< Updated upstream
        for (int i = 0; i < _globals.products.size(); i++) {
            TBoughtProducts product = _globals.products.get(i);
            Random random = new Random();

            int leftDayForProd = product.expiry_time - (_globals.day - product.buydate);
=======
        for (int i = 0; i < globals.products.size(); i++) {
            TBoughtProducts product = globals.products.get(i);
            Random random = new Random();

            int leftDayForProd = product.expiry_time - (globals.day - product.buydate);
>>>>>>> Stashed changes
            int maxCustomers = (int) (Math.round(((double) product.quantity / leftDayForProd))
                    * (product.originalPrice / product.price));
            int sellAmount = random.nextInt(0, maxCustomers + 1);

<<<<<<< Updated upstream
            _globals.money += product.price * sellAmount;

            if (sellAmount >= product.quantity) {
                _globals.products.remove(product);
            } else {
                _globals.products.get(i).quantity -= sellAmount;
=======
            globals.money += product.price * sellAmount;

            if (sellAmount >= product.quantity) {
                globals.products.remove(product);
            } else {
                globals.products.get(i).quantity -= sellAmount;
>>>>>>> Stashed changes
            }
        }
    }
>>>>>>> origin/frontend

}
