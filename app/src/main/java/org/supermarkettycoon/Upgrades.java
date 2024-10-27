package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

class StoredButtons {
    ArrayList<TBoughtProducts> buttons = new ArrayList<TBoughtProducts>();
}

public class Upgrades extends JTabbedPane {
    EventBus eventBus;
    Globals globals;
    StoredButtons storedButtons = new StoredButtons();

    @Subscribe
    public void redoProductsPage(NewDayEvent nde) {
        SwingUtilities.invokeLater(() -> {

            // Collect products to remove instead of modifying during iteration
            ArrayList<TBoughtProducts> productsToRemove = new ArrayList<TBoughtProducts>();
            globals.products.forEach(product -> {
                if (product.expiry_time == globals.day - product.buydate || product.quantity <= 10) {
                    productsToRemove.add(product);
                }
            });
            // Remove products after iteration
            globals.products.removeAll(productsToRemove);

            eventBus.post(storedButtons);

            redoEverything(globals);


            if (globals.day % 10 == 0) {
                JScrollPane products = new Products(this.globals, eventBus, true, storedButtons);
                removeTabAt(2);
                addTab("Products", products);
                setSelectedIndex(2);
            }
        });

    }

    @Subscribe
    public void redoEverything(Globals globals) {
        JScrollPane licenses = new LicenseUpgrades(this.globals, eventBus);
        JScrollPane market = new MarketUpgrades(this.globals, eventBus);
        JScrollPane products = new Products(this.globals, eventBus, false, storedButtons);

        int currentIndex = getSelectedIndex();


        removeAll();
        addTab("Licenses", licenses);
        addTab("Upgrades", market);
        addTab("Products", products);

        setSelectedIndex(currentIndex);
    }

    public Upgrades(Globals globals, EventBus eventBus) {
        this.eventBus = eventBus;
        this.globals = globals;

        JScrollPane licenses = new LicenseUpgrades(globals, eventBus);
        JScrollPane market = new MarketUpgrades(globals, eventBus);
        JScrollPane products = new Products(globals, eventBus, true, storedButtons);

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
        assert licensesJSONStream != null;
        Scanner licensesJSONScanner = new Scanner(licensesJSONStream).useDelimiter("\\A");
        String licensesJSONString = licensesJSONScanner.hasNext() ? licensesJSONScanner.next() : "";

        Gson gson = new Gson();
        TLicense[] licenses = gson.fromJson(licensesJSONString, TLicense[].class);

        for (TLicense license : licenses) {
            JButton button = new JButton(
                    "<html><center>" + license.fullname + " License" + "<br/>" + license.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));


            if (license.price >= globals.money || globals.power <= 0) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }

            button.setFont(new Font("Arial", Font.PLAIN, 18));

            globals.licenses.forEach(tLicense -> {
                if (tLicense.name.equals(license.name)) {
                    button.setEnabled(false);
                }
            });

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
        assert upgradesJSONStream != null;
        Scanner upgradesJSONScanner = new Scanner(upgradesJSONStream).useDelimiter("\\A");
        String upgradesJSONString = upgradesJSONScanner.hasNext() ? upgradesJSONScanner.next() : "";

        Gson gson = new Gson();
        TUpgrade[] upgrades = gson.fromJson(upgradesJSONString, TUpgrade[].class);

        for (TUpgrade upgrade : upgrades) {
            JButton button = new JButton(
                    "<html><center>" + upgrade.fullname + "<br/>" + upgrade.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
            if (upgrade.price >= globals.money || globals.power <= 0) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }

            button.setFont(new Font("Arial", Font.PLAIN, 18));

            globals.upgrades.forEach(tUpgrade -> {
                if (tUpgrade.name.equals(upgrade.name)) {
                    button.setEnabled(false);
                }
            });

            button.addActionListener(e -> {
                globals.upgrades.add(upgrade);
                globals.power--;
                globals.money -= upgrade.price;
                button.setEnabled(false);

                eventBus.post(globals);
            });

            innerPanel.add(button, c);
        }

        setViewportView(innerPanel);

        upgradesJSONScanner.close();
    }
}

class Products extends JScrollPane {
    Products(Globals globals, EventBus eventBus, boolean redoPrices, StoredButtons storedButtons) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1f};
        JPanel innerPanel = new JPanel(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.ipadx = 10;

        innerPanel.setLayout(layout);

        InputStream productsJSONStream = getClass().getResourceAsStream("Products.json");
        assert productsJSONStream != null;
        Scanner productsJSONScanner = new Scanner(productsJSONStream).useDelimiter("\\A");
        String productsJSONString = productsJSONScanner.hasNext() ? productsJSONScanner.next() : "";

        Gson gson = new Gson();
        TProduct[] products = gson.fromJson(productsJSONString, TProduct[].class);


        if (redoPrices) {
            storedButtons.buttons = new ArrayList<TBoughtProducts>();

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


                // Reflects the product listing to the TBoughtProducts interface to be saved
                TBoughtProducts boughtProduct = new TBoughtProducts();
                boughtProduct.fullname = product.fullname;
                boughtProduct.quantity = 1;
                boughtProduct.price = price;
                boughtProduct.originalPrice = price;
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

                storedButtons.buttons.add(boughtProduct);

            }
        }
        for (TBoughtProducts product : storedButtons.buttons) {
            product.buydate = globals.day;
            product.quantity = 1;
            JButton button = new JButton(String.format(
                    "<html><center>%s<br/>%.2f$</center></html>", product.fullname, product.price));
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

            boolean doesUserOwnLicense = false;
            boolean doesUserOwnAisle = false;

            if (product.requires_license.equals("none")) {
                doesUserOwnLicense = true;
            } else {
                for (TLicense license : globals.licenses) {
                    if (license.name.equals(product.requires_license)) {
                        doesUserOwnLicense = true;
                        break;
                    }
                }
            }

            if (product.requires_aisle.equals("none")) {
                doesUserOwnAisle = true;
            } else {
                for (TUpgrade upgrade : globals.upgrades) {
                    if (upgrade.name.equals(product.requires_aisle)) {
                        doesUserOwnAisle = true;
                        break;
                    }
                }
            }


            if (product.price > globals.money || globals.power <= 0 || !doesUserOwnLicense || !doesUserOwnAisle) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            innerPanel.add(button, c);


            button.addActionListener(e -> {
                TBoughtProducts existingProduct = globals.products.stream()
                        .filter(boughtProduct -> boughtProduct.name.equals(product.name) && boughtProduct.buydate == globals.day)
                        .findAny()
                        .orElse(null);

                if (existingProduct != null) {
                    System.out.println(existingProduct.quantity);
                    int index = globals.products.indexOf(existingProduct);
                    existingProduct.quantity += 2;
                    globals.products.set(index, existingProduct);
                    TBoughtProducts updatedProduct = globals.products.get(index);
                    globals.products.remove(index);
                    globals.products.add(index, updatedProduct);
                } else {
                    globals.products.add(product);
                }

                globals.power--;
                globals.money -= product.price;

                eventBus.post(globals);
            });


        }


        setViewportView(innerPanel);

        productsJSONScanner.close();
    }
}