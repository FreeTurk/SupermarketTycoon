package org.supermarkettycoon;

import javax.swing.*;
import java.awt.*;

import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;

public class Upgrades extends JTabbedPane {
  public Upgrades() {

    JPanel licenses = new LicenseUpgrades();
    JPanel market = new MarketUpgrades();
    JScrollPane products = new Products();

    addTab("Licenses", licenses);
    addTab("Upgrades", market);
    addTab("Products", products);
  }
}

class LicenseUpgrades extends JPanel {
  LicenseUpgrades() {

  }

}

class MarketUpgrades extends JPanel {
  MarketUpgrades() {

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
      JButton button = new JButton(product.fullname);
      button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      innerPanel.add(button, c);
    }

    setViewportView(innerPanel);
  }
}