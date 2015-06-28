package it_minds.dk.eindberetningmobil_android.controllers;

import android.location.Location;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.interfaces.OnLocationChangedCallback;
import it_minds.dk.eindberetningmobil_android.location.LocationMgr;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

/**
 * Created by kasper on 28-06-2015.
 */
public class MonitoringController implements OnLocationChangedCallback {


    private MonitoringActivity view;
    private LocationMgr locMgr;


    private double currentDistance = 0.0d;

    private List<Location> registeredPoints = new ArrayList<>();
    private Location lastLocation;
    private View.OnClickListener onResumeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isStopped = false;
            //verify that we are within "range" of the previous fix.

        }
    };
    private View.OnClickListener onPauseClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //TODO here validate that we are Withing bounds to continue, otherwise stop.
            isStopped = true;
            view.getPauseButton().setOnClickListener(onResumeClicked);
        }
    };
    private View.OnClickListener onStopClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isStopped = true;
        }
    };
    private boolean isStopped = false;


    public MonitoringController(MonitoringActivity view) {
        this.view = view;
        locMgr = LocationMgr.getInstance(view);

    }

    public void startListing() {
        locMgr.registerOnLocationChanged(this);
        view.getPauseButton().setOnClickListener(onPauseClicked);
        view.getStopButton().setOnClickListener(onStopClicked);
        registeredPoints.clear();//just in case.
    }


    public void stopListning() {
        locMgr.unRegisterOnLocationChanged(this);
    }


    @Override
    public void onNewLocation(Location location) {

        if (view == null || isStopped) {
            return;
        }
        registeredPoints.add(location);
        if(lastLocation==null){
            lastLocation = location;
            return;
        }

        updateCurrentDistance(location);
        view.getAccTextView().setText(location.getAccuracy() + " m"); //meter is an SI unit, so no translations is needed
        view.getCurrentDistanceTextView().setText(currentDistance + " Km");//kilometer is an SI unit, so no translations is needed
    }

    private void updateCurrentDistance(Location location) {
        currentDistance += Math.abs(location.distanceTo(lastLocation));
        lastLocation = location;

    }


}


