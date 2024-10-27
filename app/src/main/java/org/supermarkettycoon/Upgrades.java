package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

// Class to store button data for each product, used to track items in the Products tab
class StoredButtons {
    ArrayList<TBoughtProducts> buttons = new ArrayList<TBoughtProducts>();
}

public class Upgrades extends JTabbedPane {
    EventBus eventBus;
    Globals globals;
    StoredButtons storedButtons = new StoredButtons();

    // Event listener to refresh products and remove expired items each day
    @Subscribe
    public void redoProductsPage(NewDayEvent nde) {
        SwingUtilities.invokeLater(() -> {

            // Collect products to remove instead of modifying during iteration
            ArrayList<TBoughtProducts> productsToRemove = new ArrayList<TBoughtProducts>();
            globals.products.forEach(product -> {
                // Marks products for removal if they are expired or have zero quantity
                if (product.expiry_time <= globals.day - product.buydate || product.quantity <= 0) {
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

    // Event listener to refresh all tabs when a global update occurs
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

    // Constructor to initialize the upgrades panel with default tabs
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

// Class to display available licenses in the License Upgrades tab
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

        // Reads licenses data from JSON file
        InputStream licensesJSONStream = getClass().getResourceAsStream("Licenses.json");
        assert licensesJSONStream != null;
        Scanner licensesJSONScanner = new Scanner(licensesJSONStream).useDelimiter("\\A");
        String licensesJSONString = licensesJSONScanner.hasNext() ? licensesJSONScanner.next() : "";

        Gson gson = new Gson();
        TLicense[] licenses = gson.fromJson(licensesJSONString, TLicense[].class); 

        for (TLicense license : licenses) {
            // Creates a button for each license
            JButton button = new JButton(
                    "<html><center>" + license.fullname + " License" + "<br/>" + license.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

            // Disables button if user cannot afford or has zero power
            if (license.price >= globals.money || globals.power <= 0) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }

            button.setFont(new Font("Arial", Font.PLAIN, 18));

            // Checks if the user already owns the license and disables it if so
            globals.licenses.forEach(tLicense -> {
                if (tLicense.name.equals(license.name)) {
                    button.setEnabled(false);
                }
            });

            // Action listener to purchase the license
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

// Class to display available market upgrades in the Market Upgrades tab
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
        
        // Reads upgrades data from JSON file
        InputStream upgradesJSONStream = getClass().getResourceAsStream("Upgrades.json");
        assert upgradesJSONStream != null;
        Scanner upgradesJSONScanner = new Scanner(upgradesJSONStream).useDelimiter("\\A");
        String upgradesJSONString = upgradesJSONScanner.hasNext() ? upgradesJSONScanner.next() : "";

        Gson gson = new Gson();
        TUpgrade[] upgrades = gson.fromJson(upgradesJSONString, TUpgrade[].class);

        for (TUpgrade upgrade : upgrades) {
            // Creates a button for each upgrade
            JButton button = new JButton(
                    "<html><center>" + upgrade.fullname + "<br/>" + upgrade.price + "$" + "</center></html>");
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

            // Disables button if user cannot afford or has zero power
            if (upgrade.price >= globals.money || globals.power <= 0) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }

            button.setFont(new Font("Arial", Font.PLAIN, 18));

            // Checks if the user already owns the upgrade and disables it if so
            globals.upgrades.forEach(tUpgrade -> {
                if (tUpgrade.name.equals(upgrade.name)) {
                    button.setEnabled(false);
                }
            });

            // Action listener to purchase the upgrade
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
    // Constructor initializes the Products tab, displaying available products based on conditions
    Products(Globals globals, EventBus eventBus, boolean redoPrices, StoredButtons storedButtons) {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{1f}; 
        JPanel innerPanel = new JPanel(layout); 

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.ipadx = 10;

        innerPanel.setLayout(layout);
        
        // Loads product data from JSON file
        InputStream productsJSONStream = getClass().getResourceAsStream("Products.json");
        assert productsJSONStream != null;
        Scanner productsJSONScanner = new Scanner(productsJSONStream).useDelimiter("\\A");
        String productsJSONString = productsJSONScanner.hasNext() ? productsJSONScanner.next() : "";

        Gson gson = new Gson();
        TProduct[] products = gson.fromJson(productsJSONString, TProduct[].class); 

        // If redoPrices is true, initializes the prices and adds products to storedButtons
        if (redoPrices) {
            storedButtons.buttons = new ArrayList<TBoughtProducts>();

            for (TProduct product : products) {
                double min_price = product.min_price;
                double max_price = product.max_price;

                // Adjusts prices based on the season
                if (product.preferred_season.equals(globals.season())) {
                    min_price *= 1 + product.preferred_season_price_increase_percentage / 100;
                    max_price *= 1 + product.preferred_season_price_increase_percentage / 100;
                }
                if (product.non_preferred_season.equals(globals.season())) {
                    min_price *= 1 - product.non_preferred_season_price_decrease_percentage / 100;
                    max_price *= 1 - product.non_preferred_season_price_decrease_percentage / 100;
                }

                // Sets a random price within the adjusted range
                Random random = new Random();
                double price = random.nextDouble() * (max_price - min_price) + min_price;

                // Creates and saves a TBoughtProducts object with the generated price
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

        // Creates a button for each product and configures its display and functionality
        for (TBoughtProducts product : storedButtons.buttons) {
            product.buydate = globals.day;
            product.quantity = 1;
            JButton button = new JButton(String.format(
                    "<html><center>%s<br/>%.2f$</center></html>", product.fullname, product.price));
            button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

            boolean doesUserOwnLicense = false;
            boolean doesUserOwnAisle = false;
            // Checks if the user owns the required license for this product
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
            // Checks if the user has the required aisle for this product
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

            // Disables button if user can't afford product, lacks power, or lacks required licenses/aisles
            if (product.price > globals.money || globals.power <= 0 || !doesUserOwnLicense || !doesUserOwnAisle) {
                button.setForeground(Color.red);
                button.setEnabled(false);
            }
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            innerPanel.add(button, c);

            // Action listener to purchase the product
            button.addActionListener(e -> {
                // Finds existing product purchased on the same day to increase quantity
                TBoughtProducts existingProduct = globals.products.stream()
                        .filter(boughtProduct -> boughtProduct.name.equals(product.name) && boughtProduct.buydate == globals.day)
                        .findFirst()
                        .orElse(null);

                if (existingProduct != null) {
                    // If product already exists for today, increment its quantity
                    int index = globals.products.indexOf(existingProduct);
                    existingProduct.quantity += 1;
                    globals.products.set(index, existingProduct);
                } else {
                    // Creates a new product instance if it's the first purchase of the day
                    TBoughtProducts newProduct = new TBoughtProducts();
                    newProduct.fullname = product.fullname;
                    newProduct.quantity = 1;
                    newProduct.price = product.price;
                    newProduct.originalPrice = product.originalPrice;
                    newProduct.min_price = product.min_price;
                    newProduct.max_price = product.max_price;
                    newProduct.preferred_season = product.preferred_season;
                    newProduct.non_preferred_season = product.non_preferred_season;
                    newProduct.expiry_time = product.expiry_time;
                    newProduct.name = product.name;
                    newProduct.buydate = globals.day;
                    newProduct.requires_aisle = product.requires_aisle;
                    newProduct.requires_license = product.requires_license;
                    newProduct.non_preferred_season_price_decrease_percentage = product.non_preferred_season_price_decrease_percentage;
                    newProduct.preferred_season_price_increase_percentage = product.preferred_season_price_increase_percentage;

                    globals.products.add(newProduct); 
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
