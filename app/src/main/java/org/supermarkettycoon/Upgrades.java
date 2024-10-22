package org.supermarkettycoon;

import javax.swing.*;

public class Upgrades extends JTabbedPane {
  public Upgrades() {

    JPanel licenses = new LicenseUpgrades();
    JPanel market = new MarketUpgrades();

    addTab("Licenses", licenses);
    addTab("Upgrades", market);
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