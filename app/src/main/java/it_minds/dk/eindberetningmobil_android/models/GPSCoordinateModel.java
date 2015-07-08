package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * GPSCoordinateModel
 *
 * @license see ..
 */
public class GPSCoordinateModel {
    private String Latitude;
    private String Longitude;
    private String TimeStamp;

    public GPSCoordinateModel(String latitude, String longitude, String timeStamp) {
        Latitude = latitude;
        Longitude = longitude;
        TimeStamp = timeStamp;
    }

    /**
     * parseFromJson description here
     *
     * @return GPSCoordinateModel
     */
    public static GPSCoordinateModel parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String Latitude = obj.optString("Latitude");
        String Longitude = obj.optString("Longitude");
        String TimeStamp = obj.optString("TimeStamp");
        return new GPSCoordinateModel(Latitude, Longitude, TimeStamp);
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
    public String getLatitude() {
        return this.Latitude;
    }

    /**
     * @return String
     */
    public void setLatitude(String newVal) {
        this.Latitude = newVal;
    }

    /**
     * @return String
     */
    public String getLongitude() {
        return this.Longitude;
    }

    /**
     * @return String
     */
    public void setLongitude(String newVal) {
        this.Longitude = newVal;
    }

    /**
     * @return String
     */
    public String getTimeStamp() {
        return this.TimeStamp;
    }

    /**
     * @return String
     */
    public void setTimeStamp(String newVal) {
        this.TimeStamp = newVal;
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
        result.put("TimeStamp", TimeStamp);
        return result;

    }
}