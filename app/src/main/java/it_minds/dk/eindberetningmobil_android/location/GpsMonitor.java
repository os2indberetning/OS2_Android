/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * GPS Top layer, handles interaction with the LocationManager
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
