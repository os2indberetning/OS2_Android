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
 * Employments
 *
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

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employments that = (Employments) o;

        if (Id != that.Id) return false;
        return !(EmploymentPosition != null ? !EmploymentPosition.equals(that.EmploymentPosition) : that.EmploymentPosition != null);

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (EmploymentPosition != null ? EmploymentPosition.hashCode() : 0);
        return result;
    }
}