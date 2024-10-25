package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Scanner;

public class Upgrades extends JTabbedPane {
    EventBus eventBus;
    Globals globals;

    @Subscribe
    public void redoProductsPage(NewDayEvent nde) {

        if (globals.day % 10 == 0) {
            JScrollPane products = new Products(this.globals, eventBus);
            removeTabAt(2);
            addTab("Products", products);
            setSelectedIndex(2);
        }
    }

    public Upgrades(Globals globals, EventBus eventBus) {
        this.eventBus = eventBus;
        this.globals = globals;

        JScrollPane licenses = new LicenseUpgrades(globals, eventBus);
        JScrollPane market = new MarketUpgrades(globals, eventBus);
        JScrollPane products = new Products(globals, eventBus);

        addTab("Licenses", licenses);
        addTab("Upgrades", market);
        addTab("Products", products);
    }


}

class LicenseUpgrades extends JScrollPane {
    LicenseUpgrades(Globals globals, EventBus eventBus) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1f};
        JPanel innerPanel = new JPanel(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.ipady = 10;

        innerPanel.setLayout(layout);

        InputStream licensesJSONStream = getClass().getResourceAsStream("Licenses.json");
        Scanner licensesJSONScanner = new Scanner(licensesJSONStream).useDelimiter("\\A");
        String licensesJSONString = licensesJSONScanner.hasNext() ? licensesJSONScanner.next() : "";

        Gson gson = new Gson();
        TLicense[] licenses = gson.fromJson(licensesJSONString, TLicense[].class);

        for (TLicense license : licenses) {
            JButton button = new JButton(
                    "<html><center>" + license.fullname + " License" + "<br/>" + license.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
            button.setFont(new Font("Arial", Font.PLAIN, 24));

            if (globals.licenses.contains(license)) {
                button.setEnabled(false);
            }

            button.addActionListener(e -> {
                globals.licenses.add(license);
                globals.power--;
                globals.money -= license.price;
                button.setEnabled(false);

                eventBus.post(globals);
            });


            innerPanel.add(button, c);
        }

        setViewportView(innerPanel);

        licensesJSONScanner.close();
    }

}

class MarketUpgrades extends JScrollPane {
    MarketUpgrades(Globals globals, EventBus eventBus) {

        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1f};
        JPanel innerPanel = new JPanel(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.ipady = 10;

        innerPanel.setLayout(layout);

        InputStream upgradesJSONStream = getClass().getResourceAsStream("Upgrades.json");
        Scanner upgradesJSONScanner = new Scanner(upgradesJSONStream).useDelimiter("\\A");
        String upgradesJSONString = upgradesJSONScanner.hasNext() ? upgradesJSONScanner.next() : "";

        Gson gson = new Gson();
        TUpgrade[] upgrades = gson.fromJson(upgradesJSONString, TUpgrade[].class);

        for (TUpgrade upgrade : upgrades) {
            JButton button = new JButton(
                    "<html><center>" + upgrade.fullname + "<br/>" + upgrade.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
            button.setFont(new Font("Arial", Font.PLAIN, 24));


            button.addActionListener(e -> {
                globals.upgrades.add(upgrade);
                globals.power--;
                globals.money -= upgrade.price;
                button.setEnabled(false);

                eventBus.post(globals);
            });

            if (globals.upgrades.contains(upgrade)) {
                button.setEnabled(false);
            }
            innerPanel.add(button, c);
        }

        setViewportView(innerPanel);

        upgradesJSONScanner.close();
    }
}

class Products extends JScrollPane {
    Products(Globals globals, EventBus eventBus) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1f};
        JPanel innerPanel = new JPanel(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.ipadx = 10;

        innerPanel.setLayout(layout);

        InputStream productsJSONStream = getClass().getResourceAsStream("Products.json");
        Scanner productsJSONScanner = new Scanner(productsJSONStream).useDelimiter("\\A");
        String productsJSONString = productsJSONScanner.hasNext() ? productsJSONScanner.next() : "";

        Gson gson = new Gson();
        TProduct[] products = gson.fromJson(productsJSONString, TProduct[].class);

        for (TProduct product : products) {
            double min_price = product.min_price;
            double max_price = product.max_price;

            if (product.preferred_season.equals(globals.season())) {
                min_price *= 1 + product.preferred_season_price_increase_percentage / 100;
                max_price *= 1 + product.preferred_season_price_increase_percentage / 100;
            }

            if (product.non_preferred_season.equals(globals.season())) {
                min_price *= 1 - product.non_preferred_season_price_decrease_percentage / 100;
                max_price *= 1 - product.non_preferred_season_price_decrease_percentage / 100;
            }

            Random random = new Random();

            double price = random.nextDouble() * (max_price - min_price) + min_price;

            JButton button = new JButton(String.format(
                    "<html><center>%s<br/>%.2f$</center></html>", product.fullname, price));
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            innerPanel.add(button, c);

            // Reflects the product listing to the TBoughtProducts interface to be saved
            TBoughtProducts boughtProduct = new TBoughtProducts();
            boughtProduct.fullname = product.fullname;
            boughtProduct.quantity = 1;
            boughtProduct.price = price;
            boughtProduct.min_price = min_price;
            boughtProduct.max_price = max_price;
            boughtProduct.preferred_season = product.preferred_season;
            boughtProduct.non_preferred_season = product.non_preferred_season;
            boughtProduct.expiry_time = product.expiry_time;
            boughtProduct.name = product.name;
            boughtProduct.buydate = globals.day;
            boughtProduct.requires_aisle = product.requires_aisle;
            boughtProduct.requires_license = product.requires_license;
            boughtProduct.non_preferred_season_price_decrease_percentage = product.non_preferred_season_price_decrease_percentage;
            boughtProduct.preferred_season_price_increase_percentage = product.preferred_season_price_increase_percentage;


            button.addActionListener(e -> {
                boolean isUnique = false;

                for (int i = 0; i < globals.products.size(); i++) {
                    if (globals.products.get(i).isTwoProductsEqual(boughtProduct)) {
                        globals.products.get(i).quantity = globals.products.get(i).quantity + 1;
                        isUnique = true;
                        break;
                    }
                }

                if (!isUnique) {
                    globals.products.add(boughtProduct);
                }

                globals.power--;

                eventBus.post(globals);
            });

            if (globals.products.contains(boughtProduct)) {
                button.setEnabled(false);
            }


        }

        setViewportView(innerPanel);

        productsJSONScanner.close();
    }
}