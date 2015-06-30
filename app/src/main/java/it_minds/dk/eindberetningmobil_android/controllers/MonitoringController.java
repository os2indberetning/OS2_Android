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
 */
public class MonitoringController implements OnLocationChangedCallback {


    private MonitoringActivity view;
    private LocationMgr locMgr;


    private double currentDistance = 0.0d;

    private ArrayList<Location> registeredPoints = new ArrayList<>();
    private Location lastLocation;
    private View.OnClickListener onResumeClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isStopped = false;
            Log.e("temp", "continuing");
            //verify that we are within "range" of the previous fix.

        }
    };
    private View.OnClickListener onPauseClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("temp", "pausing");
            //TODO here validate that we are Withing bounds to continue, otherwise stop.
            isStopped = true;
            view.getPauseButton().setOnClickListener(onResumeClicked);
        }
    };
    private View.OnClickListener onStopClicked = new View.OnClickListener() {
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
                    //resume.
                    isStopped = false;
                }
            });
            dialog.showDialog();

        }
    };
    private boolean isStopped = false;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private DateTime startTime;


    public MonitoringController(MonitoringActivity view) {
        this.view = view;
        locMgr = LocationMgr.getInstance(view);

    }

    public void startListing() {
        locMgr.registerOnLocationChanged(this);
        view.getPauseButton().setOnClickListener(onPauseClicked);
        view.getStopButton().setOnClickListener(onStopClicked);
        registeredPoints.clear();//just in case.
        updateDisplay(0, 0, null);
        startTime = new DateTime();
    }


    public void stopListening() {
        locMgr.unRegisterOnLocationChanged(this);

    }


    @Override
    public void onNewLocation(Location location) {

        if (view == null || isStopped) {
            return;
        }
        registeredPoints.add(location);
        if (lastLocation == null) {
            lastLocation = location;
            return;
        }
        updateCurrentDistance(location);
        long offset = TimeZone.getDefault().getOffset(location.getTime());
        updateDisplay(location.getAccuracy(), currentDistance, new DateTime(offset + location.getTime()));

    }

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

    private void updateCurrentDistance(Location location) {
        if (location.getSpeed() > 0) {
            currentDistance += Math.abs(location.distanceTo(lastLocation));
            lastLocation = location;
        }
    }


}



