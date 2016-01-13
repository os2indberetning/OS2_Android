/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

public class DriveReport {
    private Tokens token;
    private DrivingReport report;
    private int profileId;

    public DriveReport(Tokens token, DrivingReport report, int profileId) {
        this.token = token;
        this.report = report;
        this.profileId = profileId;
    }

    public SafeJsonHelper saveAsJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("DriveReport", report.saveToJson(profileId));
        result.put("Token", token.saveToJson());
        return result;
    }
}
