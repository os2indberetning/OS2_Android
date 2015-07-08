package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Employments
 *
 * @license see ..
 */
public class Employments {
    private int Id;
    private String EmploymentPosition;

    public Employments(int id, String employmentPosition) {
        Id = id;
        EmploymentPosition = employmentPosition;

    }

    /**
     * parseFromJson description here
     *
     * @return Employments
     */
    public static Employments parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        int Id = obj.optInt("Id");
        String EmploymentPosition = obj.optString("EmploymentPosition");
        return new Employments(Id, EmploymentPosition);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Employments>
     */
    public static ArrayList<Employments> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Employments> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return int
     */
    public int getId() {
        return this.Id;
    }

    /**
     * @return int
     */
    public void setId(int newVal) {
        this.Id = newVal;
    }

    /**
     * @return String
     */
    public String getEmploymentPosition() {
        return this.EmploymentPosition;
    }

    /**
     * @return String
     */
    public void setEmploymentPosition(String newVal) {
        this.EmploymentPosition = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Id", Id);
        result.put("EmploymentPosition", EmploymentPosition);
        return result;

    }
}