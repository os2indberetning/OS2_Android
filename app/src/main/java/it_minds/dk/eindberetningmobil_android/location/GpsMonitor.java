/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

/**
 * GPS Top layer, handles interaction with the LocationManager
 */
public class GpsMonitor {

    private Runnable callback = null;
    private LocationManager locationManager;

    public GpsMonitor(Runnable callback) {
        this.callback = callback;
    }

    public void stopListening() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            locationManager.unregisterGnssStatusCallback(statusCallback);
        } else {
            locationManager.removeGpsStatusListener(listener);
        }
    }


    @SuppressLint("MissingPermission")
    public void startListening(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            locationManager.registerGnssStatusCallback(context.getMainExecutor(), statusCallback);
        }
        else {
            locationManager.addGpsStatusListener(listener);
        }
    }

    private final GpsStatus.Listener listener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            if (event == GpsStatus.GPS_EVENT_STARTED || event == GpsStatus.GPS_EVENT_STOPPED) {
                tryCallback();
            }
        }
    };

    private GnssStatus.Callback statusCallback = new GnssStatus.Callback() {
        @Override
        public void onStarted() {
            tryCallback();
        }

        @Override
        public void onStopped() {
            tryCallback();
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
