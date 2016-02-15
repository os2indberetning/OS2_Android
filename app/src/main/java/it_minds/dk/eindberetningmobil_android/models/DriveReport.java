/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.models;

import it_minds.dk.eindberetningmobil_android.models.internal.Authorization;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

public class DriveReport {
    private Authorization auth;
    private DrivingReport report;
    private int profileId;

    public DriveReport(Authorization auth, DrivingReport report, int profileId) {
        this.auth = auth;
        this.report = report;
        this.profileId = profileId;
    }

    public SafeJsonHelper saveAsJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Authorization", auth.saveGuIdToJson());
        result.put("DriveReport", report.saveToJson(profileId));
        return result;
    }
}
