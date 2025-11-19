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
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.interfaces.OnLocationChangedCallback;

/**
 * Handles the GPS interaction.
 * it automatically listens for gps when there are listeners attached, otherwise it doest (save battery)
 */
public class LocationMgr implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //<editor-fold desc="variables">
    private static final String TAG = "LocationMgr";
    private final LocationRequest mLocationRequest;
    private final GoogleApiClient mGoogleApiClient;

    private final ArrayList<OnLocationChangedCallback> onLocationChangedCallbacks = new ArrayList<>();

    //</editor-fold>


    //<editor-fold desc="Singleton pattern">
    private static LocationMgr instance;

    public static LocationMgr getInstance(Context context) {
        if (instance == null) {
            instance = new LocationMgr(context);
        }
        return instance;
    }

    private LocationMgr(Context context) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //we are going to need speed, and thus we need "High" acc.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }
    //</editor-fold>

    /**
     * when everything is working, this gets called.
     */
    @Override
    public void onLocationChanged(Location location) {
        for (OnLocationChangedCallback callback : onLocationChangedCallbacks) {
            if (callback != null) {
                callback.onNewLocation(location);
            }
        }
    }


    //<editor-fold desc="callbacks & management">

    /**
     * Callback from google services.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mGoogleApiClient.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mGoogleApiClient.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Location permissions not granted");
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * If location changes are paused..
     *
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    /**
     * If starting gettings locations failed.
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("temp", "connection failed.");
    }

    public void registerOnLocationChanged(OnLocationChangedCallback callback) {
        this.onLocationChangedCallbacks.add(callback);
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    public void unRegisterOnLocationChanged(OnLocationChangedCallback callback) {
        this.onLocationChangedCallbacks.remove(callback);
        if (onLocationChangedCallbacks.size() == 0) {
            mGoogleApiClient.disconnect();
        }
    }


    //</editor-fold>
}
