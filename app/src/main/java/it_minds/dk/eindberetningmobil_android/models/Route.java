/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Route
 */
public class Route {
    private double TotalDistance;
    private ArrayList<GPSCoordinateModel> GPSCoordinates;

    public Route(double totalDistance, ArrayList<GPSCoordinateModel> gpsCoordinates) {
        GPSCoordinates = gpsCoordinates;
        TotalDistance = totalDistance;
    }

    /**
     * parseFromJson description here
     *
     * @return Route
     */
    public static Route parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        double TotalDistance = obj.optDouble("TotalDistance");
        ArrayList<GPSCoordinateModel> GPSCoordinates = GPSCoordinateModel.parseAllFromJson(obj.optJSONArray("GPSCoordinates"));
        return new Route(TotalDistance, GPSCoordinates);
    }


    /**
     * @return double
     */
    public double getTotalDistance() {
        return this.TotalDistance;
    }

    /**
     * @return ArrayList<GPSCoordinateModel>
     */
    public ArrayList<GPSCoordinateModel> getGPSCoordinates() {
        return this.GPSCoordinates;
    }


    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("TotalDistance", TotalDistance);
        result.put("", new JSONArray());
        return result;
    }
}