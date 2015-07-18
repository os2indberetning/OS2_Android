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


    public GPSCoordinateModel(String latitude, String longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    /**
     * parseFromJson description here
     *
     * @return GPSCoordinateModel
     */
    public static GPSCoordinateModel parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String Latitude = obj.optString("Latitude");
        String Longitude = obj.optString("Longitude");
        return new GPSCoordinateModel(Latitude, Longitude);
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
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Latitude", Latitude);
        result.put("Longitude", Longitude);
        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPSCoordinateModel that = (GPSCoordinateModel) o;

        if (Latitude != null ? !Latitude.equals(that.Latitude) : that.Latitude != null)
            return false;
        if (Longitude != null ? !Longitude.equals(that.Longitude) : that.Longitude != null)
            return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = Latitude != null ? Latitude.hashCode() : 0;
        result = 31 * result + (Longitude != null ? Longitude.hashCode() : 0);
        return result;
    }
}