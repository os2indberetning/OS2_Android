package it_minds.dk.eindberetningmobil_android.models.internal;

import org.json.JSONObject;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Model representing users information
 */
public class Authorization {

    private String guId;

    public Authorization(String guId) {
        this.guId = guId;
    }

    public String getGuId() {
        return guId;
    }

    public JSONObject saveGuIdToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("GuId", guId);
        return result;
    }
}
