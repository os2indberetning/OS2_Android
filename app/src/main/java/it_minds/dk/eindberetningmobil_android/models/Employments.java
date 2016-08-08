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
    private boolean FourKmRuleAllowed;

    public Employments(int id, String employmentPosition, boolean fourKmRuleAllowed) {
        Id = id;
        EmploymentPosition = employmentPosition;
        FourKmRuleAllowed = fourKmRuleAllowed;
    }

    /**
     * parseFromJson description here
     *
     * @return Employments
     */
    public static Employments parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        int Id = obj.optInt("Id");
        String EmploymentPosition = obj.optString("EmploymentPosition");
        boolean FourKmRuleAllowed = obj.optJSONObject("OrgUnit").optBoolean("FourKmRuleAllowed");
        return new Employments(Id, EmploymentPosition, FourKmRuleAllowed);
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
     * @return boolean
     */
    public boolean getFourKmRuleAllowed() {
        return this.FourKmRuleAllowed;
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

        SafeJsonHelper fourKmObj = new SafeJsonHelper();
        fourKmObj.put("FourKmRuleAllowed", FourKmRuleAllowed);
        result.put("OrgUnit", fourKmObj);

        return result;

    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employments that = (Employments) o;

        if (Id != that.Id) return false;
        if (FourKmRuleAllowed != that.FourKmRuleAllowed) return false;
        return !(EmploymentPosition != null ? !EmploymentPosition.equals(that.EmploymentPosition) : that.EmploymentPosition != null);

    }

    @Override
    public int hashCode() {
        int result = Id;
        result = 31 * result + (EmploymentPosition != null ? EmploymentPosition.hashCode() : 0);
        return result;
    }
}