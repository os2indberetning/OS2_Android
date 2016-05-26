/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models.internal;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Model used for pre-filling new drive report.
 */
public class PrefilledData {
    private String purposeText;
    private String rateId;
    private String orgId;

    public PrefilledData(String purposeText, String rateId, String orgId) {
        this.purposeText = purposeText;
        this.rateId = rateId;
        this.orgId = orgId;
    }

    /**
     * @return String
     */
    public String getRateId() {
        return this.rateId;
    }

    /**
     * @return String
     */
    public String getOrgId() {
        return this.orgId;
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