package it_minds.dk.eindberetningmobil_android.location;

import android.content.Context;
import android.location.GpsStatus;
import android.location.LocationManager;

/**
 * Created by kasper on 28-07-2015.
 */
public class GpsMonitor {

    private Runnable callback = null;
    private LocationManager locationManager;

    public GpsMonitor(Runnable callback) {
        this.callback = callback;
    }

    public void stopListening(Context context) {
        locationManager.removeGpsStatusListener(listener);

    }

    public void startListening(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(listener);
    }

    private final GpsStatus.Listener listener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            if (event == GpsStatus.GPS_EVENT_STARTED || event == GpsStatus.GPS_EVENT_STOPPED) {
                tryCallback();
            }
        }
    };

    public boolean isGpsActive() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void tryCallback() {
        if (callback != null) {
            callback.run();
        }
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager loc = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return loc.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
