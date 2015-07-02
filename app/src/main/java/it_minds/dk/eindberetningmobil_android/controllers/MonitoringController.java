package it_minds.dk.eindberetningmobil_android.controllers;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;

import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnLocationChangedCallback;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.location.LocationMgr;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.views.AfterTripActivity;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationEndDrivingDialog;

/**
 * Created by kasper on 28-06-2015.
 * this controller handles the interaction with the gps.
 */
public class MonitoringController implements OnLocationChangedCallback {


    //<editor-fold desc="locale variables and point management">
    private final MonitoringActivity view;
    private final LocationMgr locMgr;


    private double currentDistance = 0.0d;

    private final ArrayList<Location> registeredPoints = new ArrayList<>();
    private  Location lastLocation;
    private boolean validateLocation = false;
    //</editor-fold>

    //<editor-fold desc="onclick listeners">
    /**
     * when we resume from pause.
     */
    private final View.OnClickListener onResumeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isStopped = false;
            Log.e("temp", "continuing");
            view.showResumed();
            validateLocation = true;
            view.getPauseButton().setOnClickListener(onPauseClicked);
        }
    };

    /**
     * when we click the pause button.
     */
    private final View.OnClickListener onPauseClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("temp", "pausing");
            view.showPaused();
            isStopped = true;
            view.getPauseButton().setOnClickListener(onResumeClicked);
        }
    };
    /**
     * when  we click the stop button.
     */
    private final View.OnClickListener onStopClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isStopped = true;
            Log.e("temp", "stopping.");
            ConfirmationEndDrivingDialog dialog = new ConfirmationEndDrivingDialog(view, "er du sikker du vil stoppe ?");
            dialog.setCallback(new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean endedAtHome) {
                    Intent intent = new Intent(view, AfterTripActivity.class);
                    DrivingReport report = view.getReport();
                    report.setgpsPoints(registeredPoints);
                    report.setendedAtHome(endedAtHome);
                    report.setdistanceInMeters(currentDistance);
                    report.setstartTime(startTime);
                    report.setendTime(new DateTime());
                    intent.putExtra(IntentIndexes.DATA_INDEX, report);
                    view.startActivity(intent);
                    view.finish();
                }

                @Override
                public void onError(Exception error) {
                    restartListening();
                }
            });
            dialog.showDialog();

        }
    };
    //</editor-fold>

    //<editor-fold desc="handling listening and of display format">
    private boolean isStopped = false;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private DateTime startTime;
    //</editor-fold>


    public MonitoringController(MonitoringActivity view) {
        this.view = view;
        locMgr = LocationMgr.getInstance(view);
    }

    public void startListing() {
        Log.e("temp", "starting to listen...");
        locMgr.registerOnLocationChanged(this);
        view.getPauseButton().setOnClickListener(onPauseClicked);
        view.getStopButton().setOnClickListener(onStopClicked);
        registeredPoints.clear();//just in case.
        updateDisplay(0, 0, null);
        startTime = new DateTime();
    }


    public void stopListening() {
        locMgr.unRegisterOnLocationChanged(this);
        Log.e("temp", "stop listening");
    }

    /**
     *
     */
    private void restartListening() {
        Log.e("temp", "starting to listen. again..");
        isStopped = false;
        locMgr.registerOnLocationChanged(this);
    }

    /**
     * callback for a new location.
     *
     * @param location
     */
    @Override
    public void onNewLocation(Location location) {
        //if we are validating
        if (validateLocation) {
            handleValidationOnResume(location);
            return;
        }
        //are we allowed to listen ?
        if (view == null || isStopped) {
            Log.e("temp", "discared location");
            return;
        }
        //yes yes , so lets handle the new location (update the distance, and update the displays)
        handleNewLocation(location);
    }

    /**
     * handle the new location (update the distance, and update the displays)
     *
     * @param location
     */
    private void handleNewLocation(Location location) {
        registeredPoints.add(location);
        if (lastLocation == null) {
            lastLocation = location;
            return;
        }

        updateCurrentDistance(location);
        long offset = TimeZone.getDefault().getOffset(location.getTime());
        updateDisplay(location.getAccuracy(), currentDistance, new DateTime(offset + location.getTime()));
    }

    /**
     * handles the validation if we are "resuming". it validate that the current location is close enough
     * to the old, otherwise we have encountered an error (the user might have moved further away or alike).
     *
     * @param location
     */
    private void handleValidationOnResume(Location location) {
        Log.e("temp", "is validating location");
        if (location.getAccuracy() <= 50) {
            Log.e("temp", "validation point is semi precise." + location.getAccuracy());

            validateLocation = false;
            Location lastLocation = registeredPoints.get(registeredPoints.size() - 1);
            Log.e("temp", "validation is within: " + lastLocation.distanceTo(location));

            if (lastLocation.distanceTo(location) < 200) {
                //ok to contine.
                onNewLocation(location);//resume the function.
            } else {
                //Not allowed to continue
                view.showInvalidLocation();
            }
        }
    }

    /**
     * updates the GUI.
     *
     * @param acc
     * @param distance
     * @param time
     */
    private void updateDisplay(float acc, double distance, DateTime time) {
        view.getAccTextView().setText(acc + " m"); //meter is an SI unit, so no translations is needed
        String s = decimalFormat.format(distance / 1000.0d);
        view.getCurrentDistanceTextView().setText(s + " Km");//kilometer is an SI unit, so no translations is needed
        if (time != null) {
            view.getLastFixTextView().setText(time.toString("HH:mm:ss"));
        } else {
            view.getLastFixTextView().setText("");
        }
    }

    /**
     * updates the current distance.
     *
     * @param location
     */
    private void updateCurrentDistance(Location location) {
        if (location.getSpeed() > 0) {
            currentDistance += Math.abs(location.distanceTo(lastLocation));
            lastLocation = location;
        }
    }


}



