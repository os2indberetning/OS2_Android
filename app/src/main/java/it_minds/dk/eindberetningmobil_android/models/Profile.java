package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Profile
 *
 * @license see ..
 */
public class Profile {
    private int Id;
    private String Firstname;
    private String Lastname;
    private String HomeLatitude;
    private String HomeLongitude;
    private ArrayList<Employments> Employments;
    private ArrayList<Tokens> Tokens;

    public Profile(int id, String firstname, String lastname, String homeLatitude, String homeLongitude, ArrayList<Employments> employments, ArrayList<Tokens> tokens) {
        Id = id;
        Firstname = firstname;
        Lastname = lastname;
        HomeLatitude = homeLatitude;
        HomeLongitude = homeLongitude;
        Employments = employments;
        Tokens = tokens;
    }

    /**
     * parseFromJson description here
     *
     * @return Profile
     */
    public static Profile parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        int Id = obj.optInt("Id");
        String Firstname = obj.optString("Firstname");
        String Lastname = obj.optString("Lastname");
        String HomeLatitude = obj.optString("HomeLatitude");
        String HomeLongitude = obj.optString("HomeLongitude");
        ArrayList<Tokens> tokens = it_minds.dk.eindberetningmobil_android.models.Tokens.parseAllFromJson(obj.optJSONArray("Tokens"));
        ArrayList<Employments> Employments = it_minds.dk.eindberetningmobil_android.models.Employments.parseAllFromJson(obj.optJSONArray("Employments"));
        return new Profile(Id, Firstname, Lastname, HomeLatitude, HomeLongitude, Employments, tokens);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Profile>
     */
    public static List<Profile> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Profile> result = new ArrayList<>();
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
    public String getFirstname() {
        return this.Firstname;
    }

    /**
     * @return String
     */
    public void setFirstname(String newVal) {
        this.Firstname = newVal;
    }

    /**
     * @return String
     */
    public String getLastname() {
        return this.Lastname;
    }

    /**
     * @return String
     */
    public void setLastname(String newVal) {
        this.Lastname = newVal;
    }

    /**
     * @return String
     */
    public String getHomeLatitude() {
        return this.HomeLatitude;
    }

    /**
     * @return String
     */
    public void setHomeLatitude(String newVal) {
        this.HomeLatitude = newVal;
    }

    /**
     * @return String
     */
    public String getHomeLongitude() {
        return this.HomeLongitude;
    }

    /**
     * @return String
     */
    public void setHomeLongitude(String newVal) {
        this.HomeLongitude = newVal;
    }

    /**
     * @return ArrayList<Employments>
     */
    public ArrayList<Employments> getEmployments() {
        return this.Employments;
    }

    /**
     * @return ArrayList<Employments>
     */
    public void setEmployments(ArrayList<Employments> newVal) {
        this.Employments = newVal;
    }

    /**
     * @return ArrayList<Tokens>
     */
    public ArrayList<Tokens> getTokens() {
        return this.Tokens;
    }

    /**
     * @return ArrayList<Tokens>
     */
    public void setTokens(ArrayList<Tokens> newVal) {
        this.Tokens = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Id", Id);
        result.put("Firstname", Firstname);
        result.put("Lastname", Lastname);
        result.put("HomeLatitude", HomeLatitude);
        result.put("HomeLongitude", HomeLongitude);

        JSONArray emplouymentArr = new JSONArray();
        for (it_minds.dk.eindberetningmobil_android.models.Employments emp : getEmployments()) {
            emplouymentArr.put(emp.saveToJson());
        }
        result.put("Employments", emplouymentArr);


        JSONArray tokenArray = new JSONArray();
        for (it_minds.dk.eindberetningmobil_android.models.Tokens token : getTokens()) {
            tokenArray.put(token.saveToJson());
        }

        result.put("Tokens", tokenArray);

        return result;

    }
}