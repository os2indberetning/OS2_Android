/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Purpose
 */
public class Purpose implements Comparable<Purpose>{
    private String description;
    private Date lastUsed;


    public Purpose(String description, Date lastUsed) {
        this.description = description;
        this.lastUsed = lastUsed;
    }

    /**
     * parseFromJson description here
     *
     * @return Purpose
     */
    public static Purpose parseFromJson(JSONObject obj) throws JSONException, MalformedURLException, ParseException {
        String description = obj.optString("Description");
        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH).parse(obj.optString("Date"));
        return new Purpose(description, date);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Purpose>
     */
    public static ArrayList<Purpose> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException, ParseException {
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
        return this.description;
    }

    public void setDescription(String newVal) {
        this.description = newVal;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    /**
     * saveToJson description and lastUsed here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Description", description);
        result.put("Date", lastUsed.toString());
        return result;

    }

    public static JSONArray saveAllToJson(List<Purpose> purposeListList) {
        JSONArray arr = new JSONArray();
        for (Purpose p : purposeListList) {
            arr.put(p.saveToJson());
        }
        return arr;
    }

    @Override
    public int compareTo(@NonNull Purpose otherDate) {
        return otherDate.getLastUsed().compareTo(this.getLastUsed());
    }

    @Override
    public boolean equals(Object otherPurpose) {
        return this.getDescription().equals(((Purpose) otherPurpose).getDescription());
    }
}