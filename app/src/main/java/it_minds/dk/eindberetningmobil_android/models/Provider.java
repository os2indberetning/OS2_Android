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
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Provider
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
    public String getAPIUrl() {
        return this.APIUrl;
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
    public String getTextColor() {
        return this.TextColor;
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
    public String getSecondaryColor() {
        return this.SecondaryColor;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        if (Name != null ? !Name.equals(provider.Name) : provider.Name != null) return false;
        if (APIUrl != null ? !APIUrl.equals(provider.APIUrl) : provider.APIUrl != null)
            return false;
        if (ImgUrl != null ? !ImgUrl.equals(provider.ImgUrl) : provider.ImgUrl != null)
            return false;
        if (TextColor != null ? !TextColor.equals(provider.TextColor) : provider.TextColor != null)
            return false;
        if (PrimaryColor != null ? !PrimaryColor.equals(provider.PrimaryColor) : provider.PrimaryColor != null)
            return false;
        return !(SecondaryColor != null ? !SecondaryColor.equals(provider.SecondaryColor) : provider.SecondaryColor != null);

    }

    @Override
    public int hashCode() {
        int result = Name != null ? Name.hashCode() : 0;
        result = 31 * result + (APIUrl != null ? APIUrl.hashCode() : 0);
        result = 31 * result + (ImgUrl != null ? ImgUrl.hashCode() : 0);
        result = 31 * result + (TextColor != null ? TextColor.hashCode() : 0);
        result = 31 * result + (PrimaryColor != null ? PrimaryColor.hashCode() : 0);
        result = 31 * result + (SecondaryColor != null ? SecondaryColor.hashCode() : 0);
        return result;
    }
}