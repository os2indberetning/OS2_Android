package it_minds.dk.eindberetningmobil_android.interfaces;

import android.location.Location;

/**
 * Created by kasper on 28-06-2015.
 * describes a callback event for listening for location updates.
 * (Observer pattern)
 */
public interface OnLocationChangedCallback {
    void onNewLocation(Location location);
}
