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
 * Profile
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
    public String getLastname() {
        return this.Lastname;
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
    public String getHomeLongitude() {
        return this.HomeLongitude;
    }

    /**
     * @return ArrayList<Employments>
     */
    public ArrayList<Employments> getEmployments() {
        return this.Employments;
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
        if (getEmployments() != null) {
            for (it_minds.dk.eindberetningmobil_android.models.Employments emp : getEmployments()) {
                emplouymentArr.put(emp.saveToJson());
            }
        }
        result.put("Employments", emplouymentArr);


        JSONArray tokenArray = new JSONArray();
        if (getTokens() != null) {
            for (it_minds.dk.eindberetningmobil_android.models.Tokens token : getTokens()) {
                tokenArray.put(token.saveToJson());
            }
        }
        result.put("Tokens", tokenArray);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (Id != profile.Id) return false;
        if (Firstname != null ? !Firstname.equals(profile.Firstname) : profile.Firstname != null)
            return false;
        if (Lastname != null ? !Lastname.equals(profile.Lastname) : profile.Lastname != null)
            return false;
        if (HomeLatitude != null ? !HomeLatitude.equals(profile.HomeLatitude) : profile.HomeLatitude != null)
            return false;
        if (HomeLongitude != null ? !HomeLongitude.equals(profile.HomeLongitude) : profile.HomeLongitude != null)
            return false;
        if (Employments != null ? !Employments.equals(profile.Employments) : profile.Employments != null)
            return false;
        return !(Tokens != null ? !Tokens.equals(profile.Tokens) : profile.Tokens != null);

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (Firstname != null ? Firstname.hashCode() : 0);
        result = 31 * result + (Lastname != null ? Lastname.hashCode() : 0);
        result = 31 * result + (HomeLatitude != null ? HomeLatitude.hashCode() : 0);
        result = 31 * result + (HomeLongitude != null ? HomeLongitude.hashCode() : 0);
        result = 31 * result + (Employments != null ? Employments.hashCode() : 0);
        result = 31 * result + (Tokens != null ? Tokens.hashCode() : 0);
        return result;
    }
}