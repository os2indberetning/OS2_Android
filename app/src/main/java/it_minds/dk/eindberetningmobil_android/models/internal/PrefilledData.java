/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models.internal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

public class PrefilledData {
    private String purposeText;

    public PrefilledData(String purposeText, String rateId, String orgId) {
        this.purposeText = purposeText;
        this.rateId = rateId;
        this.orgId = orgId;
    }

    /**
     * @return String
     */
    public String getPurposeText() {
        return this.purposeText;
    }


    /**
     * @return String
     */
    public void setPurposeText(String newVal) {
        this.purposeText = newVal;
    }

    private String rateId;

    /**
     * @return String
     */
    public String getRateId() {
        return this.rateId;
    }


    /**
     * @return String
     */
    public void setRateId(String newVal) {
        this.rateId = newVal;
    }

    private String orgId;

    /**
     * @return String
     */
    public String getOrgId() {
        return this.orgId;
    }


    /**
     * @return String
     */
    public void setOrgId(String newVal) {
        this.orgId = newVal;
    }


    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("purposeText", purposeText);
        result.put("rateId", rateId);
        result.put("orgId", orgId);
        return result;

    }

    /**
     * parseAllFromJson description here
     *
     * @return ArrayList<PrefilledData>
     */
    public static ArrayList<PrefilledData> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<PrefilledData> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * parseFromJson description here
     *
     * @return PrefilledData
     */
    public static PrefilledData parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        if (obj == null) {
            return null;
        }
        String purposeText = obj.optString("purposeText");
        String rateId = obj.optString("rateId");
        String orgId = obj.optString("orgId");
        return new PrefilledData(purposeText, rateId, orgId);
    }


}