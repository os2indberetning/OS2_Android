package it_minds.dk.eindberetningmobil_android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.server.SafeJsonHelper;

/**
 * Token
 */
public class Token {
    private String guid;
    private String tokenString;
    private String status;


    public Token(String guid, String tokenString, String status) {
        this.guid = guid;
        this.tokenString = tokenString;
        this.status = status;
    }

    /**
     * parseFromJson description here
     *
     * @return Token
     */
    public static Token parseFromJson(JSONObject obj) throws JSONException, MalformedURLException {
        String guid = obj.optString("guid");
        String tokenString = obj.optString("tokenString");
        String status = obj.optString("status");
        return new Token(guid, tokenString, status);
    }

    /**
     * parseAllFromJson description here
     *
     * @return List<Token>
     */
    public static List<Token> parseAllFromJson(JSONArray arr) throws JSONException, MalformedURLException {
        ArrayList<Token> result = new ArrayList<>();
        for (int i = 0; i < arr.length(); i++) {
            result.add(parseFromJson(arr.getJSONObject(i)));
        }
        return result;
    }

    /**
     * @return String
     */
    public String getguid() {
        return this.guid;
    }

    /**
     * @return String
     */
    public void setguid(String newVal) {
        this.guid = newVal;
    }

    /**
     * @return String
     */
    public String gettokenString() {
        return this.tokenString;
    }

    /**
     * @return String
     */
    public void settokenString(String newVal) {
        this.tokenString = newVal;
    }

    /**
     * @return String
     */
    public String getstatus() {
        return this.status;
    }

    /**
     * @return String
     */
    public void setstatus(String newVal) {
        this.status = newVal;
    }

    /**
     * saveToJson description here
     *
     * @return JSONObject
     */
    public JSONObject saveToJson() {
        SafeJsonHelper result = new SafeJsonHelper();
        result.put("guid", guid);
        result.put("tokenString", tokenString);
        result.put("status", status);
        return result;

    }
}