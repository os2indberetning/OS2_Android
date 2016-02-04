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
 * Purpose
 */
public class Purpose {
    private String Description;
    private int Uses;


    public Purpose(String description, int uses) {
        Description = description;
        Uses = uses;
    }

    /**
     * parseFromJson description here
     *
     * @return Purpose
     */
    public static Purpose parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String Description = obj.optString("Description");
        int Uses = obj.optInt("Uses");
        return new Purpose(Description, Uses);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Purpose>
     */
    public static ArrayList<Purpose> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Purpose> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public String getDescription() {
        return this.Description;
    }

    /**
     * @return String
     */
    public void setDescription(String newVal) {
        this.Description = newVal;
    }

    /**
     * @return int
     */
    public int getUses() {
        return this.Uses;
    }

    /**
     * @return int
     */
    public void setUses(int newVal) {
        this.Uses = newVal;
    }

    /**
     * saveToJson description and uses here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Description", Description);
        result.put("Uses", Uses);
        return result;

    }

    public static JSONArray saveAllToJson(List<Purpose> purposeListList) {
        JSONArray arr = new JSONArray();
        for (Purpose p : purposeListList) {
            arr.put(p.saveToJson());
        }
        return arr;
    }
}