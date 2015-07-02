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
 * Describes the various providers we can use / work with.
 */
public class Provider {
    private final String name;
    private final String apiUrl;
    private final String imgUrl;
    private final String textColor;
    private final String primaryColor;
    private final String secondaryColor;


    public Provider(String name, String apiUrl, String imgUrl, String textColor, String primaryColor, String secondaryColor) {

        this.name = name;
        this.apiUrl = apiUrl;
        this.imgUrl = imgUrl;
        this.textColor = textColor;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public String getName() {
        return name;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
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
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Name", name);
        result.put("APIUrl", apiUrl);
        result.put("ImgUrl", imgUrl);
        result.put("TextColor", textColor);
        result.put("PrimaryColor", primaryColor);
        result.put("SecondaryColor", secondaryColor);
        return result;

    }
}