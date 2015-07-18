package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Tokens
 *
 * @license see ..
 */
public class Tokens {
    private String GuId;
    private String TokenString;
    private int Status;


    public Tokens(String guId, String tokenString, int status) {
        GuId = guId;
        TokenString = tokenString;
        Status = status;
    }

    /**
     * parseFromJson description here
     *
     * @return Tokens
     */
    public static Tokens parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String GuId = obj.optString("GuId");
        String TokenString = obj.optString("TokenString");
        int Status = obj.optInt("Status");
        return new Tokens(GuId, TokenString, Status);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Tokens>
     */
    public static ArrayList<Tokens> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Tokens> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public String getGuId() {
        return this.GuId;
    }

    /**
     * @return String
     */
    public void setGuId(String newVal) {
        this.GuId = newVal;
    }

    /**
     * @return String
     */
    public String getTokenString() {
        return this.TokenString;
    }

    /**
     * @return String
     */
    public void setTokenString(String newVal) {
        this.TokenString = newVal;
    }

    /**
     * @return int
     */
    public int getStatus() {
        return this.Status;
    }

    /**
     * @return int
     */
    public void setStatus(int newVal) {
        this.Status = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("GuId", GuId);
        result.put("TokenString", TokenString);
        result.put("Status", Status);
        return result;

    }

    @Override
    public boolean equals(Object o) {
        
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tokens tokens = (Tokens) o;

        if (Status != tokens.Status) return false;
        if (GuId != null ? !GuId.equals(tokens.GuId) : tokens.GuId != null) return false;
        return !(TokenString != null ? !TokenString.equals(tokens.TokenString) : tokens.TokenString != null);

    }

    @Override
    public int hashCode() {
        int result = GuId != null ? GuId.hashCode() : 0;
        result = 31 * result + (TokenString != null ? TokenString.hashCode() : 0);
        result = 31 * result + Status;
        return result;
    }
}