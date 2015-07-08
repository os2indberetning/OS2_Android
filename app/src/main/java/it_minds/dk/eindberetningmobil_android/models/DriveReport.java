package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * DriveReport
 *
 * @license see ..
 */
public class DriveReport {
    private String Purpose;
    private String Date;
    private String ManualEntryRemark;
    private boolean EndsAtHome;
    private boolean StartsAtHome;
    private int ProfileId;
    private int EmploymentId;
    private int RateId;
    private Route route;

    public DriveReport(String purpose, String date, String manualEntryRemark, boolean endsAtHome, boolean startsAtHome, int profileId, int employmentId, int rateId, Route route) {
        Purpose = purpose;
        Date = date;
        ManualEntryRemark = manualEntryRemark;
        EndsAtHome = endsAtHome;
        StartsAtHome = startsAtHome;
        ProfileId = profileId;
        EmploymentId = employmentId;
        RateId = rateId;
        this.route = route;
    }

    /**
     * parseFromJson description here
     *
     * @return DriveReport
     */
    public static DriveReport parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String Purpose = obj.optString("Purpose");
        String Date = obj.optString("Date");
        String ManualEntryRemark = obj.optString("ManualEntryRemark");
        boolean EndsAtHome = obj.optBoolean("EndsAtHome");
        boolean StartsAtHome = obj.optBoolean("StartsAtHome");
        int ProfileId = obj.optInt("ProfileId ");
        int EmploymentId = obj.optInt("EmploymentId");
        int RateId = obj.optInt("RateId");
        Route route = Route.parseFromJson(obj.optJSONObject("Route"));
        return new DriveReport(Purpose, Date, ManualEntryRemark, EndsAtHome, StartsAtHome, ProfileId, EmploymentId, RateId, route);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<DriveReport>
     */
    public static List<DriveReport> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<DriveReport> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public String getPurpose() {
        return this.Purpose;
    }

    /**
     * @return String
     */
    public void setPurpose(String newVal) {
        this.Purpose = newVal;
    }

    /**
     * @return String
     */
    public String getDate() {
        return this.Date;
    }

    /**
     * @return String
     */
    public void setDate(String newVal) {
        this.Date = newVal;
    }

    /**
     * @return String
     */
    public String getManualEntryRemark() {
        return this.ManualEntryRemark;
    }

    /**
     * @return String
     */
    public void setManualEntryRemark(String newVal) {
        this.ManualEntryRemark = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getEndsAtHome() {
        return this.EndsAtHome;
    }

    /**
     * @return boolean
     */
    public void setEndsAtHome(boolean newVal) {
        this.EndsAtHome = newVal;
    }

    /**
     * @return boolean
     */
    public boolean getStartsAtHome() {
        return this.StartsAtHome;
    }

    /**
     * @return boolean
     */
    public void setStartsAtHome(boolean newVal) {
        this.StartsAtHome = newVal;
    }

    /**
     * @return int
     */
    public int getProfileId() {
        return this.ProfileId;
    }

    /**
     * @return int
     */
    public void setProfileId(int newVal) {
        this.ProfileId = newVal;
    }

    /**
     * @return int
     */
    public int getEmploymentId() {
        return this.EmploymentId;
    }

    /**
     * @return int
     */
    public void setEmploymentId(int newVal) {
        this.EmploymentId = newVal;
    }

    /**
     * @return int
     */
    public int getRateId() {
        return this.RateId;
    }

    /**
     * @return int
     */
    public void setRateId(int newVal) {
        this.RateId = newVal;
    }

    /**
     * @return Route
     */
    public Route getroute() {
        return this.route;
    }

    /**
     * @return Route
     */
    public void setroute(Route newVal) {
        this.route = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("Purpose", Purpose);
        result.put("Date", Date);
        result.put("ManualEntryRemark", ManualEntryRemark);
        result.put("EndsAtHome", EndsAtHome);
        result.put("StartsAtHome", StartsAtHome);
        result.put("ProfileId ", ProfileId);
        result.put("EmploymentId", EmploymentId);
        result.put("RateId", RateId);
        result.put("Route", route);
        return result;

    }
}