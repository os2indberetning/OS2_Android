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
 * UserInfo
 */
public class UserInfo {
    private Profile profile;
    private ArrayList<Rates> rates;

    public UserInfo(Profile profile, ArrayList<Rates> rates) {
        this.profile = profile;
        this.rates = rates;
    }

    /**
     * parseFromJson description here
     *
     * @return UserInfo
     */
    public static UserInfo parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        Profile profile = Profile.parseFromJson(obj.optJSONObject("profile"));
        ArrayList<Rates> rates = Rates.parseAllFromJson(obj.optJSONArray("rates"));
        return new UserInfo(profile, rates);
    }

    /**
     * @return Profile
     */
    public Profile getprofile() {
        return this.profile;
    }


    /**
     * @return ArrayList<Rates>
     */
    public ArrayList<Rates> getrates() {
        return this.rates;
    }


    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Profile", profile);
        result.put("Rates", new JSONArray(rates));

        return result;

    }
}