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

import it_minds.dk.eindberetningmobil_android.models.internal.Authorization;
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

    //Part of new username/password login
    private Authorization authorization;

    public Profile(int id, String firstname, String lastname, String homeLatitude, String homeLongitude, ArrayList<Employments> employments, Authorization authorization) {
        this.Id = id;
        this.Firstname = firstname;
        this.Lastname = lastname;
        this.HomeLatitude = homeLatitude;
        this.HomeLongitude = homeLongitude;
        this.Employments = employments;
        this.authorization = authorization;
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
        ArrayList<Employments> Employments = it_minds.dk.eindberetningmobil_android.models.Employments.parseAllFromJson(obj.optJSONArray("Employments"));

        String authGuId = obj.optJSONObject("Authorization").optString("GuId");

        return new Profile(Id, Firstname, Lastname, HomeLatitude, HomeLongitude, Employments, new Authorization(authGuId));
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


    public Authorization getAuthorization() {
        return authorization;
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

        result.put("Authorization", authorization.saveGuIdToJson());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile otherProfile = (Profile) o;

        if (Id != otherProfile.Id) return false;
        if (Firstname != null ? !Firstname.equals(otherProfile.Firstname) : otherProfile.Firstname != null)
            return false;
        if (Lastname != null ? !Lastname.equals(otherProfile.Lastname) : otherProfile.Lastname != null)
            return false;
        if (HomeLatitude != null ? !HomeLatitude.equals(otherProfile.HomeLatitude) : otherProfile.HomeLatitude != null)
            return false;
        if (HomeLongitude != null ? !HomeLongitude.equals(otherProfile.HomeLongitude) : otherProfile.HomeLongitude != null)
            return false;
        if (Employments != null ? !Employments.equals(otherProfile.Employments) : otherProfile.Employments != null)
            return false;
        return !(authorization != null ? !authorization.getGuId().equals(otherProfile.authorization.getGuId()) : otherProfile.authorization.getGuId() != null);
    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (Firstname != null ? Firstname.hashCode() : 0);
        result = 31 * result + (Lastname != null ? Lastname.hashCode() : 0);
        result = 31 * result + (HomeLatitude != null ? HomeLatitude.hashCode() : 0);
        result = 31 * result + (HomeLongitude != null ? HomeLongitude.hashCode() : 0);
        result = 31 * result + (Employments != null ? Employments.hashCode() : 0);
        result = 31 * result + (authorization != null ? authorization.hashCode() : 0);
        return result;
    }
}