/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * The model containing all the measured data for a DrivingReport
 */
public class DrivingReport implements Parcelable {

    private String Uuid;
    private String purpose;
    private String orgLocation;
    private String Rate;
    private String extraDescription;
    private boolean haveEditedDistance;
    private boolean startedAtHome;
    private boolean endedAtHome;
    private boolean fourKMRule;
    private DateTime startTime;
    private DateTime endTime;
    private double distanceInMeters;
    private ArrayList<GPSCoordinateModel> gpsPoints;

    public DrivingReport() {
        this.Uuid = UUID.randomUUID().toString();
        gpsPoints = new ArrayList<>();
    }

    public DrivingReport(String purpose, String orgLocation, String rate, String extraDescription, boolean haveEditedDistance, boolean startedAtHome, boolean endedAtHome, boolean fourKMRule, DateTime startTime, DateTime endTime, double distanceInMeters) {

        this.Uuid = UUID.randomUUID().toString();
        this.purpose = purpose;
        this.orgLocation = orgLocation;
        this.Rate = rate;
        this.extraDescription = extraDescription;
        this.haveEditedDistance = haveEditedDistance;
        this.startedAtHome = startedAtHome;
        this.endedAtHome = endedAtHome;
        this.fourKMRule = fourKMRule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distanceInMeters = distanceInMeters;
        this.gpsPoints = new ArrayList<>();
    }

    /**
     * @return String
     */
    public String getUuid() { return this.Uuid; }

    /**
     * @return String
     */
    public void setUuid(String newVal) { this.Uuid = newVal; }

    /**
     * @return String
     */
    public String getPurpose() {
        return this.purpose;
    }

    /**
     * @return String
     */
    public void setPurpose(String newVal) {
        this.purpose = newVal;
    }

    /**
     * @return String
     */
    public String getOrgLocation() {
        return this.orgLocation;
    }

    /**
     * @return String
     */
    public void setOrgLocation(String newVal) {
        this.orgLocation = newVal;
    }

    /**
     * @return String
     */
    public String getRate() {
        return this.Rate;
    }

    /**
     * @return String
     */
    public void setRate(String newVal) {
        this.Rate = newVal;
    }

    /**
     * @return String
     */
    public String getExtraDescription() {
        return this.extraDescription;
    }

    /**
     * @return String
     */
    public void setExtraDescription(String newVal) {
        this.extraDescription = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getHaveEditedDistance() {
        return this.haveEditedDistance;
    }

    /**
     * @return boolean
     */
    public void sethaveEditedDistance(boolean newVal) {
        this.haveEditedDistance = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getstartedAtHome() {
        return this.startedAtHome;
    }

    /**
     * @return boolean
     */
    public void setstartedAtHome(boolean newVal) {
        this.startedAtHome = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getendedAtHome() { return this.endedAtHome; }

    /**
     * @return boolean
     */
    public void setendedAtHome(boolean newVal) {
        this.endedAtHome = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getfourKMRule() { return this.fourKMRule; }

    /**
     * @return boolean
     */
    public void setfourKMRule(boolean newVal) { this.fourKMRule = newVal; }

    /**
     * @return DateTime
     */
    public DateTime getstartTime() { return this.startTime; }

    /**
     * @return DateTime
     */
    public void setstartTime(DateTime newVal) {
        this.startTime = newVal;
    }

    /**
     * @return DateTime
     */
    public void setEndTime(DateTime newVal) {
        this.endTime = newVal;
    }

    /**
     * @return double
     */
    public double getDistanceInMeters() {
        return this.distanceInMeters;
    }

    /**
     * @return double
     */
    public void setDistanceInMeters(double newVal) {
        this.distanceInMeters = newVal;
    }

    /**
     * @return ArrayList<Location>
     */
    public ArrayList<GPSCoordinateModel> getgpsPoints() {
        return this.gpsPoints;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson(int profileId) {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Uuid", Uuid);
        result.put("Purpose", purpose);
        result.put("EmploymentId", Integer.parseInt(orgLocation));
        result.put("RateId", Integer.parseInt(Rate));
        result.put("ManualEntryRemark", extraDescription);

        result.put("StartsAtHome", startedAtHome);
        result.put("EndsAtHome", endedAtHome);
        result.put("FourKmRule", fourKMRule);
        if (startTime != null) {
            result.put("Date", startTime.toString());
        } else if (endTime != null) {
            result.put("Date", endTime.toString());
        }
        //the route data inside of the report.
        SafeJsonHelper routeView = new SafeJsonHelper();
        routeView.put("TotalDistance", DistanceDisplayer.formatDisanceForUpload(distanceInMeters)); //THIS IS IN KM.NOTICE IT GRACELY!!
        JSONArray gpsPointsArray = new JSONArray();
        for (GPSCoordinateModel loc : gpsPoints) {
            JSONObject gpsPoint =loc.saveToJson();
            gpsPointsArray.put(gpsPoint);
        }
        routeView.put("GPSCoordinates", gpsPointsArray);
        result.put("route", routeView);
        result.put("ProfileId", profileId);

        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrivingReport that = (DrivingReport) o;

        if (haveEditedDistance != that.haveEditedDistance) return false;
        if (startedAtHome != that.startedAtHome) return false;
        if (endedAtHome != that.endedAtHome) return false;
        if (fourKMRule != that.fourKMRule) return false;
        if (Double.compare(that.distanceInMeters, distanceInMeters) != 0) return false;
        if (purpose != null ? !purpose.equals(that.purpose) : that.purpose != null) return false;
        if (orgLocation != null ? !orgLocation.equals(that.orgLocation) : that.orgLocation != null)
            return false;
        if (Rate != null ? !Rate.equals(that.Rate) : that.Rate != null) return false;
        if (extraDescription != null ? !extraDescription.equals(that.extraDescription) : that.extraDescription != null)
            return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null)
            return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        return !(gpsPoints != null ? !gpsPoints.equals(that.gpsPoints) : that.gpsPoints != null);

    }

    @Override
    public int hashCode() {
        int result;
        long tempdistanceInMeters;
        result = 1;
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (orgLocation != null ? orgLocation.hashCode() : 0);
        result = 31 * result + (Rate != null ? Rate.hashCode() : 0);
        result = 31 * result + (extraDescription != null ? extraDescription.hashCode() : 0);
        result = 31 * result + (haveEditedDistance ? 1 : 0);
        result = 31 * result + (startedAtHome ? 1 : 0);
        result = 31 * result + (endedAtHome ? 1 : 0);
        result = 31 * result + (fourKMRule ? 1 : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        tempdistanceInMeters = Double.doubleToLongBits(distanceInMeters);
        result = 31 * result + (int) (tempdistanceInMeters ^ (tempdistanceInMeters >>> 32));
        result = 31 * result + (gpsPoints != null ? gpsPoints.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Uuid);
        dest.writeString(this.purpose);
        dest.writeString(this.orgLocation);
        dest.writeString(this.Rate);
        dest.writeString(this.extraDescription);
        dest.writeByte(haveEditedDistance ? (byte) 1 : (byte) 0);
        dest.writeByte(startedAtHome ? (byte) 1 : (byte) 0);
        dest.writeByte(endedAtHome ? (byte) 1 : (byte) 0);
        dest.writeByte(fourKMRule ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.startTime);
        dest.writeSerializable(this.endTime);
        dest.writeDouble(this.distanceInMeters);
        dest.writeTypedList(gpsPoints);
    }

    protected DrivingReport(Parcel in) {
        this.Uuid = in.readString();
        this.purpose = in.readString();
        this.orgLocation = in.readString();
        this.Rate = in.readString();
        this.extraDescription = in.readString();
        this.haveEditedDistance = in.readByte() != 0;
        this.startedAtHome = in.readByte() != 0;
        this.endedAtHome = in.readByte() != 0;
        this.fourKMRule = in.readByte() != 0;
        this.startTime = (DateTime) in.readSerializable();
        this.endTime = (DateTime) in.readSerializable();
        this.distanceInMeters = in.readDouble();
        this.gpsPoints = in.createTypedArrayList(GPSCoordinateModel.CREATOR);
    }

    public static final Creator<DrivingReport> CREATOR = new Creator<DrivingReport>() {
        public DrivingReport createFromParcel(Parcel source) {
            return new DrivingReport(source);
        }

        public DrivingReport[] newArray(int size) {
            return new DrivingReport[size];
        }
    };
}

