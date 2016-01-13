/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import android.util.Log;

import org.json.JSONException;

import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

public class SaveableDriveReport {
    private final SaveableReport report;
    private final Tokens token;

    public SaveableDriveReport(Tokens token, SaveableReport report) {
        this.token = token;
        this.report = report;
    }

    public SafeJsonHelper saveAsJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        if(report!=null){
            try {
                result.put("DriveReport", this.report.getJsonToSend());
            } catch (JSONException e) {
                Log.e("Saved drive report","preparing for send failed;", e);
                e.printStackTrace();
            }
        }else{
            result.put("DriveReport", "");
        }
        if(token!=null){
            result.put("Token", token.saveToJson());
        }else{
            result.put("Token","");
        }
        return result;
    }
}
