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
 */
public class Employments {
    private int Id;
    private String EmploymentPosition;
    private String ManNr;

    public Employments(int id, String employmentPosition, String manNr) {
        Id = id;
        EmploymentPosition = employmentPosition;
        ManNr = manNr;
    }

    /**
     * parseFromJson description here
     *
     * @return Employments
     */
    public static Employments parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        int Id = obj.optInt("Id");
        String EmploymentPosition = obj.optString("EmploymentPosition");
        String ManNr = obj.optString("ManNr");
        return new Employments(Id, EmploymentPosition, ManNr);
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
     * @return String
     */
    public String getEmploymentPosition() {
        return this.EmploymentPosition;
    }

    /**
     * @return String
     */
    public String getManNr() {
        return this.ManNr;
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
        result.put("ManNr", ManNr);
        return result;

    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employments that = (Employments) o;

        if (Id != that.Id) return false;
        if (ManNr != that.ManNr) return false;
        return !(EmploymentPosition != null ? !EmploymentPosition.equals(that.EmploymentPosition) : that.EmploymentPosition != null);

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (EmploymentPosition != null ? EmploymentPosition.hashCode() : 0);
        result = 31 * result + (ManNr != null ? ManNr.hashCode() : 0);
        return result;
    }
}