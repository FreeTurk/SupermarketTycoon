package org.supermarkettycoon;

class TBoughtProducts extends TProduct {
  public int quantity;
  public double price;
  public int buydate;
}

public class TSaveFile {
  public String username;
  public int money;
  public int day;
  public int power;
  public TLicense[] licenses;
  public TUpgrade[] upgrades;
  public TProduct[] products;
}
