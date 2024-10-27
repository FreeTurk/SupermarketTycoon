package org.supermarkettycoon;

public class Globals extends SaveFile {

    public int day = 1;
    public double money = 1000;
    public int power = 10;

    // Lambda function to determine the season
    public String season() {
        double dayOfYear = day % 360;

        if (dayOfYear >= 0 && dayOfYear < 90) {
            return "Spring";
        } else if (dayOfYear >= 90 && dayOfYear < 180) {
            return "Summer";
        } else if (dayOfYear >= 180 && dayOfYear < 270) {
            return "Autumn";
        } else {
            return "Winter";
        }
    }

    ;

}
