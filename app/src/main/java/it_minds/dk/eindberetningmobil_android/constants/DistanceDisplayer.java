package it_minds.dk.eindberetningmobil_android.constants;

import java.text.DecimalFormat;

/**
 * Created by kasper on 12-07-2015.
 * A helper to convert a meter to km, and only use 2 decimals precission
 */
public class DistanceDisplayer {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public static String formatDistance(double distance) {
        return decimalFormat.format(distance / 1000.0d);
    }

}
