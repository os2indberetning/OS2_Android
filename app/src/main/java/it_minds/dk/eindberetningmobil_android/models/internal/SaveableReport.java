package it_minds.dk.eindberetningmobil_android.models.internal;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * SaveableReport
 */
public class SaveableReport {
    private String jsonToSend;
    private String purpose;
    private String rateid;
    private double totalDistance;
    private DateTime createdAt;

    public SaveableReport(String jsonToSend, String purpose, String rateid, double totalDistance, DateTime createdAt) {

        this.jsonToSend = jsonToSend;
        this.purpose = purpose;
        this.rateid = rateid;
        this.totalDistance = totalDistance;
        this.createdAt = createdAt;
    }

    public SaveableReport(DrivingReport report, int profileId) {
        jsonToSend = report.saveToJson(profileId).toString();
        purpose = report.getPurpose();
        rateid = report.getRate();
        totalDistance = report.getdistanceInMeters();
        createdAt = report.getstartTime();
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<SaveableReport>
     */
    public static List<SaveableReport> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<SaveableReport> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * parseFromJson description here
     *
     * @return SaveableReport
     */
    public static SaveableReport parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        if (obj == null) {
            return null;
        }
        String jsonToSend = obj.optString("jsonToSend");
        String purpose = obj.optString("purpose");
        String rateid = obj.optString("rateid");
        double totalDistance = obj.optDouble("totalDistance");
        DateTime createdAt = null;
        try {
            createdAt = new DateTime(obj.optString("createdAt"));
        } catch (Exception e) {
            
        }
        return new SaveableReport(jsonToSend, purpose, rateid, totalDistance, createdAt);
    }

    /**
     * @return String
     */
    public JSONObject getJsonToSend() throws JSONException {
        return new JSONObject(this.jsonToSend);
    }

    /**
     * @return String
     */
    public void setJsonToSend(String newVal) {
        this.jsonToSend = newVal;
    }

    /**
     * @return String
     */
    public String getPurpose() {
        return this.purpose;
    }

    /**
     * @return String
     */
    public void setPurpose(String newVal) {
        this.purpose = newVal;
    }

    /**
     * @return String
     */
    public String getRateid() {
        return this.rateid;
    }

    /**
     * @return String
     */
    public void setRateid(String newVal) {
        this.rateid = newVal;
    }

    /**
     * @return double
     */
    public double getTotalDistance() {
        return this.totalDistance;
    }

    /**
     * @return double
     */
    public void setTotalDistance(double newVal) {
        this.totalDistance = newVal;
    }

    /**
     * @return DateTime
     */
    public DateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * @return DateTime
     */
    public void setCreatedAt(DateTime newVal) {
        this.createdAt = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("jsonToSend", jsonToSend);
        result.put("purpose", purpose);
        result.put("rateid", rateid);
        result.put("totalDistance", totalDistance);
        if (createdAt != null) {
            result.put("createdAt", createdAt.toString());
        } else {
            result.put("createdAt", "");
        }

        return result;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaveableReport that = (SaveableReport) o;

        if (Double.compare(that.totalDistance, totalDistance) != 0) return false;
        if (jsonToSend != null ? !jsonToSend.equals(that.jsonToSend) : that.jsonToSend != null)
            return false;
        if (purpose != null ? !purpose.equals(that.purpose) : that.purpose != null) return false;
        if (rateid != null ? !rateid.equals(that.rateid) : that.rateid != null) return false;
        return !(createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = jsonToSend != null ? jsonToSend.hashCode() : 0;
        result = 31 * result + (purpose != null ? purpose.hashCode() : 0);
        result = 31 * result + (rateid != null ? rateid.hashCode() : 0);
        temp = Double.doubleToLongBits(totalDistance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}