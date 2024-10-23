package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Scanner;

public class Upgrades extends JTabbedPane {
  public Upgrades() {

    JScrollPane licenses = new LicenseUpgrades();
    JScrollPane market = new MarketUpgrades();
    JScrollPane products = new Products();

    addTab("Licenses", licenses);
    addTab("Upgrades", market);
    addTab("Products", products);
  }
}

class LicenseUpgrades extends JScrollPane {
  LicenseUpgrades() {
    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 1f };
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
      innerPanel.add(button, c);
    }

    setViewportView(innerPanel);

    licensesJSONScanner.close();
  }

}

class MarketUpgrades extends JScrollPane {
  MarketUpgrades() {

    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 1f };
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
          "<html><center>" + upgrade.fullname + " License" + "<br/>" + upgrade.price + "$" + "</center></html>");
      button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
      button.setFont(new Font("Arial", Font.PLAIN, 24));
      innerPanel.add(button, c);
    }

    setViewportView(innerPanel);

    upgradesJSONScanner.close();
  }
}

class Products extends JScrollPane {
  Products() {
    GridBagLayout layout = new GridBagLayout();
    layout.columnWeights = new double[] { 1f };
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
      JButton button = new JButton(
          "<html><center>" + product.fullname + "<br/>" + product.min_price + "$" + "</center></html>");
      button.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
      button.setFont(new Font("Arial", Font.PLAIN, 24));
      innerPanel.add(button, c);
    }

    setViewportView(innerPanel);

    productsJSONScanner.close();
  }
}