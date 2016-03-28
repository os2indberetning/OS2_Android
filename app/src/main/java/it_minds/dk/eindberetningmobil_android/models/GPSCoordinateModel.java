/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * GPSCoordinateModel
 */
public class GPSCoordinateModel implements Parcelable {
    private double Latitude;
    private double Longitude;
    private boolean IsViaPoint;


    public GPSCoordinateModel(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
        IsViaPoint = false;
    }

    public GPSCoordinateModel(double lat, double longitude, boolean isvia) {
        this(lat, longitude);
        this.IsViaPoint = isvia;
    }

    /**
     * parseFromJson description here
     *
     * @return GPSCoordinateModel
     */
    public static GPSCoordinateModel parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        double Latitude = obj.optDouble("Latitude");
        double Longitude = obj.optDouble("Longitude");
        boolean isVia = obj.optBoolean("IsViaPoint", false);
        return new GPSCoordinateModel(Latitude, Longitude, isVia);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<GPSCoordinateModel>
     */
    public static ArrayList<GPSCoordinateModel> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<GPSCoordinateModel> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public double getLatitude() {
        return this.Latitude;
    }

    /**
     * @return String
     */
    public double getLongitude() {
        return this.Longitude;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Latitude", Latitude);
        result.put("Longitude", Longitude);
        result.put("IsViaPoint", IsViaPoint);
        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSCoordinateModel that = (GPSCoordinateModel) o;

        if (Double.compare(that.Latitude, Latitude) != 0) return false;
        if (Double.compare(that.Longitude, Longitude) != 0) return false;
        return IsViaPoint == that.IsViaPoint;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(Latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(Longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (IsViaPoint ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.Latitude);
        dest.writeDouble(this.Longitude);
        dest.writeByte(IsViaPoint ? (byte) 1 : (byte) 0);
    }

    protected GPSCoordinateModel(Parcel in) {
        this.Latitude = in.readDouble();
        this.Longitude = in.readDouble();
        this.IsViaPoint = in.readByte() != 0;
    }

    public static final Creator<GPSCoordinateModel> CREATOR = new Creator<GPSCoordinateModel>() {
        public GPSCoordinateModel createFromParcel(Parcel source) {
            return new GPSCoordinateModel(source);
        }

        public GPSCoordinateModel[] newArray(int size) {
            return new GPSCoordinateModel[size];
        }
    };

    public void setIsViaPoint(boolean isViaPoint) {
        this.IsViaPoint = isViaPoint;
    }
}