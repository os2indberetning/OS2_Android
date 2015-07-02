package it_minds.dk.eindberetningmobil_android.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Created by kasper on 28-06-2015.
 */

/**
 * DrivingReport
 *
 * Describes a driving report.
 * NOT DONE
 */
public class DrivingReport implements Parcelable {
    private String rate;
    private String purpose;
    private String orgLocation;
    private String Rate;
    private String extraDescription;
    private boolean haveEditedDistance;
    private boolean startedAtHome;
    private boolean endedAtHome;
    private DateTime startTime;
    private DateTime endTime;
    private double distanceInMeters;
    private ArrayList<Location> gpsPoints;

    public DrivingReport() {

    }

    public DrivingReport(String purpose, String orgLocation, String rate, String extraDescription, boolean haveEditedDistance, boolean startedAtHome, boolean endedAtHome, DateTime startTime, DateTime endTime, double distanceInMeters) {

        this.purpose = purpose;
        this.orgLocation = orgLocation;
        this.rate = rate;
        this.extraDescription = extraDescription;
        this.haveEditedDistance = haveEditedDistance;
        this.startedAtHome = startedAtHome;
        this.endedAtHome = endedAtHome;
        this.startTime = startTime;
        this.endTime = endTime;
        this.distanceInMeters = distanceInMeters;
    }

    /**
     * parseFromJson description here
     *
     * @return DrivingReport
     */
    public static DrivingReport parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String purpose = obj.optString("purpose");
        String orgLocation = obj.optString("orgLocation");
        String Rate = obj.optString("Rate");
        String extraDescription = obj.optString("extraDescription");
        boolean haveEditedDistance = obj.optBoolean("haveEditedDistance");
        boolean startedAtHome = obj.optBoolean("startedAtHome");
        boolean endedAtHome = obj.optBoolean("endedAtHome");
        DateTime startTime = new DateTime(obj.optString("startTime"));
        DateTime endTime = new DateTime(obj.optString("endTime"));
        double distanceInMeters = obj.optDouble("distanceInMeters");

        return new DrivingReport(purpose, orgLocation, Rate, extraDescription, haveEditedDistance, startedAtHome, endedAtHome, startTime, endTime, distanceInMeters);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<DrivingReport>
     */
    public static List<DrivingReport> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<DrivingReport> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

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
    public boolean gethaveEditedDistance() {
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
    public boolean getendedAtHome() {
        return this.endedAtHome;
    }

    /**
     * @return boolean
     */
    public void setendedAtHome(boolean newVal) {
        this.endedAtHome = newVal;
    }

    /**
     * @return DateTime
     */
    public DateTime getstartTime() {
        return this.startTime;
    }

    /**
     * @return DateTime
     */
    public void setstartTime(DateTime newVal) {
        this.startTime = newVal;
    }

    /**
     * @return DateTime
     */
    public DateTime getendTime() {
        return this.endTime;
    }

    /**
     * @return DateTime
     */
    public void setendTime(DateTime newVal) {
        this.endTime = newVal;
    }

    /**
     * @return double
     */
    public double getdistanceInMeters() {
        return this.distanceInMeters;
    }

    /**
     * @return double
     */
    public void setdistanceInMeters(double newVal) {
        this.distanceInMeters = newVal;
    }

    /**
     * @return ArrayList<Location>
     */
    public ArrayList<Location> getgpsPoints() {
        return this.gpsPoints;
    }

    /**
     * @return ArrayList<Location>
     */
    public void setgpsPoints(ArrayList<Location> newVal) {
        this.gpsPoints = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("purpose", purpose);
        result.put("orgLocation", orgLocation);
        result.put("Rate", Rate);
        result.put("extraDescription", extraDescription);
        result.put("haveEditedDistance", haveEditedDistance);
        result.put("startedAtHome", startedAtHome);
        result.put("endedAtHome", endedAtHome);
        result.put("startTime", startTime.toString());
        result.put("endTime", endTime.toString());
        result.put("distanceInMeters", distanceInMeters);
        result.put("", new JSONArray());

        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rate);
        dest.writeString(this.purpose);
        dest.writeString(this.orgLocation);
        dest.writeString(this.Rate);
        dest.writeString(this.extraDescription);
        dest.writeByte(haveEditedDistance ? (byte) 1 : (byte) 0);
        dest.writeByte(startedAtHome ? (byte) 1 : (byte) 0);
        dest.writeByte(endedAtHome ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.startTime);
        dest.writeSerializable(this.endTime);
        dest.writeDouble(this.distanceInMeters);
        dest.writeTypedList(gpsPoints);
    }

    protected DrivingReport(Parcel in) {
        this.rate = in.readString();
        this.purpose = in.readString();
        this.orgLocation = in.readString();
        this.Rate = in.readString();
        this.extraDescription = in.readString();
        this.haveEditedDistance = in.readByte() != 0;
        this.startedAtHome = in.readByte() != 0;
        this.endedAtHome = in.readByte() != 0;
        this.startTime = (DateTime) in.readSerializable();
        this.endTime = (DateTime) in.readSerializable();
        this.distanceInMeters = in.readDouble();
        this.gpsPoints = in.createTypedArrayList(Location.CREATOR);
    }

    public static final Parcelable.Creator<DrivingReport> CREATOR = new Parcelable.Creator<DrivingReport>() {
        public DrivingReport createFromParcel(Parcel source) {
            return new DrivingReport(source);
        }

        public DrivingReport[] newArray(int size) {
            return new DrivingReport[size];
        }
    };
}

