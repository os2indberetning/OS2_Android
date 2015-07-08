package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Provider
 *
 * @license see ..
 */
public class Provider {
    private String Name;
    private String APIUrl;
    private String ImgUrl;
    private String TextColor;
    private String PrimaryColor;
    private String SecondaryColor;

    public Provider(String name, String apiUrl, String imgUrl, String textColor, String primaryColor, String secondaryColor) {
        Name = name;
        APIUrl = apiUrl;
        ImgUrl = imgUrl;
        TextColor = textColor;
        PrimaryColor = primaryColor;
        SecondaryColor = secondaryColor;
    }

    /**
     * parseFromJson description here
     *
     * @return Provider
     */
    public static Provider parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String Name = obj.optString("Name");
        String APIUrl = obj.optString("APIUrl");
        String ImgUrl = obj.optString("ImgUrl");
        String TextColor = obj.optString("TextColor");
        String PrimaryColor = obj.optString("PrimaryColor");
        String SecondaryColor = obj.optString("SecondaryColor");
        return new Provider(Name, APIUrl, ImgUrl, TextColor, PrimaryColor, SecondaryColor);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Provider>
     */
    public static List<Provider> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Provider> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public String getName() {
        return this.Name;
    }

    /**
     * @return String
     */
    public void setName(String newVal) {
        this.Name = newVal;
    }

    /**
     * @return String
     */
    public String getAPIUrl() {
        return this.APIUrl;
    }

    /**
     * @return String
     */
    public void setAPIUrl(String newVal) {
        this.APIUrl = newVal;
    }

    /**
     * @return String
     */
    public String getImgUrl() {
        return this.ImgUrl;
    }

    /**
     * @return String
     */
    public void setImgUrl(String newVal) {
        this.ImgUrl = newVal;
    }

    /**
     * @return String
     */
    public String getTextColor() {
        return this.TextColor;
    }

    /**
     * @return String
     */
    public void setTextColor(String newVal) {
        this.TextColor = newVal;
    }

    /**
     * @return String
     */
    public String getPrimaryColor() {
        return this.PrimaryColor;
    }

    /**
     * @return String
     */
    public void setPrimaryColor(String newVal) {
        this.PrimaryColor = newVal;
    }

    /**
     * @return String
     */
    public String getSecondaryColor() {
        return this.SecondaryColor;
    }

    /**
     * @return String
     */
    public void setSecondaryColor(String newVal) {
        this.SecondaryColor = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Name", Name);
        result.put("APIUrl", APIUrl);
        result.put("ImgUrl", ImgUrl);
        result.put("TextColor", TextColor);
        result.put("PrimaryColor", PrimaryColor);
        result.put("SecondaryColor", SecondaryColor);
        return result;

    }
}