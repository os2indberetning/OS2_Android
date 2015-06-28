package it_minds.dk.eindberetningmobil_android.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.controllers.MonitoringController;
import it_minds.dk.eindberetningmobil_android.interfaces.OnLocationChangedCallback;

/**
 * Created by kasper on 28-06-2015.
 * Handles the GPS interaction.
 */
public class LocationMgr implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    //<editor-fold desc="variables">
    private static final String TAG = "LocationMgr";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private ArrayList<OnLocationChangedCallback> onLocationChangedCallbacks = new ArrayList<>();

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
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
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
        Log.e(TAG, "new location");
    }


    //<editor-fold desc="callbacks & management">

    /**
     * Callback from google services.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
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

    }

    public void registerOnLocationChanged(OnLocationChangedCallback callback) {
        this.onLocationChangedCallbacks.add(callback);
    }

    public void unRegisterOnLocationChanged(OnLocationChangedCallback callback) {
        this.onLocationChangedCallbacks.remove(callback);
    }


    //</editor-fold>
}
