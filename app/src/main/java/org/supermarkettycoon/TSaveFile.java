package org.supermarkettycoon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class TBoughtProducts extends TProduct {
  public int quantity;
  public double price;
  public int buydate;
}

public class TSaveFile {
  public String username;
  public int money = 1000;
  public int day = 1;
  public int power = 10;
  public List<TLicense> licenses = new ArrayList<TLicense>();
  public List<TUpgrade> upgrades = new ArrayList<TUpgrade>();
  public List<TBoughtProducts> products = new ArrayList<TBoughtProducts>();
}

