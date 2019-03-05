/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.service;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import org.joda.time.DateTime;

import java.util.TimeZone;

import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.GPSCoordinateModel;

/**
 * an object, handling the bl (business logic), so that the monitoringservice do not have to work directly with the bl layer.
 */
public class MonitoringServiceReport {
    private static final int MINIMUM_REQURIED_ACC_IN_METERS = 100;
    private static final int MAX_DIST_RESUME_ALLOWED_IN_METERS = 200;
    private static final int MINIMUM_VALID_SPEED_METERS_PER_SECOND = 3;
    private static final int MAXIMUM_VALID_SPEED_METERS_PER_SECOND = 56;
    private MonitoringService monitoringService;

    //<editor-fold desc="report mangement">
    private DrivingReport report;
    private boolean validateOnResume = false;
    private Location lastLocation = null;
    //</editor-fold>

    private UiStatusModel lastUiUpdate;

    public MonitoringServiceReport(Intent intent, MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
        if (intent != null && intent.hasExtra(IntentIndexes.DATA_INDEX)) {
            report = intent.getParcelableExtra(IntentIndexes.DATA_INDEX);
        } else {
            report = new DrivingReport();
        }
        updateDisplay(0, 0, null);
    }


    /**
     * Callback function that gets called with a new location.
     *
     * @param location
     */
    public void addLocation(Location location) {
        if (validateOnResume) {
            handleValidationOnResume(location);
            return;
        }

        if (location.getAccuracy() <= MINIMUM_REQURIED_ACC_IN_METERS) {
            if (lastLocation == null ||
                    (calculateDistanceBetweenPoints(lastLocation, location) >= location.getAccuracy())) { // Removes jitter in reports

                double speedMetersPerSecond = 0.0;

                if (location.hasSpeed() && location.getSpeed() > 0) {
                    speedMetersPerSecond = location.getSpeed();
                } else if (lastLocation != null) {
                    double elapsedTimeInSeconds = (location.getTime() - lastLocation.getTime()) / 1000.0;
                    double distanceInMeters = lastLocation.distanceTo(location);

                    speedMetersPerSecond = distanceInMeters / elapsedTimeInSeconds;
                } else {
                    // No last location, and no speed from location, just "fake" a speed to ensure the check below succeeds
                    // ("getSpeed()" isn't guaranteed to actually return a speed)
                    speedMetersPerSecond = MINIMUM_VALID_SPEED_METERS_PER_SECOND;
                }

                // Only consider location update "valid" if speed is between 3 m/s (~11 km/h) and 56 m/s (~200 km/h)
                if (speedMetersPerSecond >= MINIMUM_VALID_SPEED_METERS_PER_SECOND && speedMetersPerSecond < MAXIMUM_VALID_SPEED_METERS_PER_SECOND) {
                    //yes yes , so lets handle the new location (update the distance, and update the displays)
                    handleNewLocation(location, false);
                } else {
                    long offset = TimeZone.getDefault().getOffset(location.getTime());
                    updateDisplay(location.getAccuracy(), report.getDistanceInMeters(), new DateTime(offset + location.getTime()));
                    Log.e("temp", "not moving");
                }
            } else {
                long offset = TimeZone.getDefault().getOffset(location.getTime());
                updateDisplay(location.getAccuracy(), report.getDistanceInMeters(), new DateTime(offset + location.getTime()));
            }

        }
    }

    /**
     * handle the new location (update the distance, and update the displays)
     *
     * @param location
     */
    private void handleNewLocation(Location location, boolean isViaPoint) {
        if (report != null && report.getgpsPoints() != null) {
            report.getgpsPoints().add(new GPSCoordinateModel(location.getLatitude(), location.getLongitude(), isViaPoint));
            if (lastLocation == null) {
                lastLocation = location;
                return;
            }
            updateCurrentDistance(location);
            long offset = TimeZone.getDefault().getOffset(location.getTime());
            updateDisplay(location.getAccuracy(), report.getDistanceInMeters(), new DateTime(offset + location.getTime()));
        }
    }

    /**
     * handles the validation if we are "resuming". it validate that the current location is close enough
     * to the old, otherwise we have encountered an error (the user might have moved further away or alike).
     *
     * @param location
     */
    private synchronized void handleValidationOnResume(Location location) {
        Log.e("temp", "is validating location");
        if (report == null || report.getgpsPoints() == null) {
            return;//this is an issue. but lets not crash
        }

        if (report.getgpsPoints().size() == 0) { //special case, if we do not have anything...
            validateOnResume = false;
            handleNewLocation(location, false);//resume the function.
            return;
        }

        if (location.getAccuracy() <= MINIMUM_REQURIED_ACC_IN_METERS) {
            Log.e("temp", "validation point is semi precise." + location.getAccuracy());
            GPSCoordinateModel lastLocation = report.getgpsPoints().get(report.getgpsPoints().size() - 1);
            float result[] = new float[5];
            Location.distanceBetween(lastLocation.getLatitude(), lastLocation.getLongitude(), location.getLatitude(), location.getLongitude(), result);
            Log.e("temp", "validation is within: " + result[0]);
            float distance = result[0];
            if (distance < MAX_DIST_RESUME_ALLOWED_IN_METERS) {
                //ok to contine.
                handleNewLocation(location, false);//resume the function.
                validateOnResume = false;
            } else {
                //Not allowed to continue
                if (monitoringService.isListening()) { //stop if listening, so we are "safe" :)
                    MonitoringService.pauseResumeListening(monitoringService);
                }
                monitoringService.sendError();
                validateOnResume = false;
            }

        }
    }

    /***
     * Creates an UI model to send to the listening activity.
     *
     * @param acc
     * @param distance
     * @param time
     */
    public void updateDisplay(float acc, double distance, DateTime time) {
        String s = DistanceDisplayer.formatDistance(distance);
        String distanceText = (s + " Km");//kilometer is an SI unit, so no translations is needed
        String timeText = "";
        if (time != null) {
            timeText = (time.toString("HH:mm:ss"));
        }
        String accText = DistanceDisplayer.formatAccuracy(acc) + " m";
        lastUiUpdate = new UiStatusModel(timeText, accText, distanceText);
        monitoringService.sendUiUpdate(lastUiUpdate);
    }

    /**
     * updates the current distance.
     *
     * @param location
     */
    private void updateCurrentDistance(Location location) {
        double currentDistance = report.getDistanceInMeters();
        report.setDistanceInMeters(currentDistance + Math.abs(location.distanceTo(lastLocation)));
        lastLocation = location;
    }

    /**
     * @return the current Km we have moved
     */
    public String getCurrentDistanceInKm() {
        return DistanceDisplayer.formatDistance(report.getDistanceInMeters());
    }

    /**
     * Simple helper to wrap the current report into a bundle.
     *
     * @return
     */
    public Bundle createNotificationBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentIndexes.DATA_INDEX, report);
        return bundle;
    }

    public void pause() {
        validateOnResume = true;
        if (report.getgpsPoints() != null && report.getgpsPoints().size() > 1) {
            report.getgpsPoints().get(report.getgpsPoints().size()-1).setIsViaPoint(true);
        }
    }

    private float calculateDistanceBetweenPoints(Location firstLocation, Location secondLocation){
        float result[] = new float[5];
        Location.distanceBetween(firstLocation.getLatitude(), firstLocation.getLongitude(), secondLocation.getLatitude(), secondLocation.getLongitude(), result);
        float distance = result[0];
        return distance;
    }

    public UiStatusModel createUiStatus() {
        return lastUiUpdate;
    }
}
