package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Route
 *
 * @license see ..
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
     * parseAllFromJson description here
     *
     * @return List<Route>
     */
    public static List<Route> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Route> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return double
     */
    public double getTotalDistance() {
        return this.TotalDistance;
    }

    /**
     * @return double
     */
    public void setTotalDistance(double newVal) {
        this.TotalDistance = newVal;
    }

    /**
     * @return ArrayList<GPSCoordinateModel>
     */
    public ArrayList<GPSCoordinateModel> getGPSCoordinates() {
        return this.GPSCoordinates;
    }

    /**
     * @return ArrayList<GPSCoordinateModel>
     */
    public void setGPSCoordinates(ArrayList<GPSCoordinateModel> newVal) {
        this.GPSCoordinates = newVal;
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