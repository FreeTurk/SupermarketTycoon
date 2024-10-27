package org.supermarkettycoon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class TBoughtProducts extends TProduct {
    public int quantity;
    public double price;
    public double originalPrice;
    public int buydate;

}

public class TSaveFile {
    public String username;
    public double money = 1000;
    public int day = 1;
    public int power = 10;
    public ArrayList<TLicense> licenses = new ArrayList<TLicense>();
    public ArrayList<TUpgrade> upgrades = new ArrayList<TUpgrade>();
    public ArrayList<TBoughtProducts> products = new ArrayList<TBoughtProducts>();
}

