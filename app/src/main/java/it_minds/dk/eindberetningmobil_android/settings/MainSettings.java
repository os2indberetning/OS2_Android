package it_minds.dk.eindberetningmobil_android.settings;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Tokens;

/**
 * Created by kasper on 28-06-2015.
 * handles all storage on the device, though sharedPrefs (a private one).
 */
public class MainSettings {


    //<editor-fold desc="Constants / indexes">
    private static final String PREF_NAME = "settings";
    private static final String PROVIDER_INDEX = "PROVIDER_INDEX";
    private static final String TOKEN_INDEX = "TOKEN_INDEX";
    //</editor-fold>

    //<editor-fold desc="singleton">

    private static MainSettings instance;

    private final Context context;

    public MainSettings(Context context) {
        this.context = context;
    }

    public static MainSettings getInstance(Context context) {
        if (instance == null) {
            instance = new MainSettings(context);
        }
        return instance;
    }

    //</editor-fold>

    //<editor-fold desc="Helper methods">
    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    //</editor-fold>

    //<editor-fold desc="Provider">

    /**
     * haveProvider description here
     *
     * @return boolean
     */
    public boolean haveProvider() {
        return getPrefs().getString(PROVIDER_INDEX, null) != null;
    }

    /**
     * getProvider description here
     *
     * @return Provider
     */
    public Provider getProvider() {
        String val = getPrefs().getString(PROVIDER_INDEX, null);
        if (val == null) {
            return null;
        }
        try {
            return Provider.parseFromJson(new JSONObject(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * setProvider description here
     *
     * @return void
     */
    public void setProvider(Provider newVal) {
        getPrefs().edit().putString(PROVIDER_INDEX, newVal.saveToJson().toString()).commit();
    }
    //</editor-fold>


    //<editor-fold desc="token">

    /**
     * haveToken description here
     *
     * @return boolean
     */
    public boolean haveToken() {
        return getPrefs().getString(TOKEN_INDEX, null) != null;
    }

    /**
     * getToken description here
     *
     * @return Tokens
     */
    public Tokens getToken() {
        String val = getPrefs().getString(TOKEN_INDEX, null);
        if (val == null) {
            return null;
        }
        try {
            return Tokens.parseFromJson(new JSONObject(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * setToken description here
     *
     * @return void
     */
    public void setToken(Tokens newVal) {
        getPrefs().edit().putString(TOKEN_INDEX, newVal.saveToJson().toString()).commit();
    }
    //</editor-fold>

}
