package it_minds.dk.eindberetningmobil_android.models;

import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Created by kasper on 01-09-2015.
 */
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
            result.put("DriveReport", this.report.getJsonToSend());
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
