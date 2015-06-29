package it_minds.dk.eindberetningmobil_android.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by kasper on 08-06-2015.
 */
public class SafeJsonHelper extends JSONObject {

    @Override
    public JSONObject put(String name, boolean value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, double value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, int value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, long value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }

    @Override
    public JSONObject put(String name, Object value) {
        try {
            return super.put(name, value);
        } catch (JSONException e) {
            Logger.getLogger("SafeJsonObject").log(Level.WARNING, "", e);
            return null;
        }
    }
}
