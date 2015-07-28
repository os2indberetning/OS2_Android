package it_minds.dk.eindberetningmobil_android.models;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Created by kasper on 28-07-2015.
 */
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
