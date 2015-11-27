package it_minds.dk.eindberetningmobil_android.constants;

import java.text.DecimalFormat;

/**
 * Created by kasper on 12-07-2015.
 * A helper to convert a meter to km, and only use 1 decimals precission
 */
public class DistanceDisplayer {
    private static DecimalFormat decimalFormat = new DecimalFormat("0.0");

    /**
     * Converts the distance in meteres to km, and displays it nicly.
     *
     * @param distance
     * @return
     */
    public static String formatDistance(double distance) {
        return decimalFormat.format(distance / 1000.0d).replace('.', ',');//danish style
    }

    public static String formatDisanceForUpload(double distance){
        return decimalFormat.format(distance / 1000.0d);//english style (With '.' as seperator)
    }

    public static String formatAccuracy(double acc){
        return decimalFormat.format(acc).replace('.', ',');//danish style
    }
}
