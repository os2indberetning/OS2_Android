package it_minds.dk.eindberetningmobil_android.settings;

import android.content.Context;
import android.content.SharedPreferences;


import it_minds.dk.eindberetningmobil_android.models.Provider;

/**
 * Created by kasper on 28-06-2015.
 */
public class MainSettings {


    //<editor-fold desc="Constants / indexes">
    private static final String PREF_NAME = "settings";
    private static final String PROVIDER_INDEX = "PROVIDER_INDEX";
    private static final String TOKEN_INDEX = "TOKEN_INDEX";
    //</editor-fold>

    //<editor-fold desc="singleton">

    private static MainSettings instance;

    private Context context;

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
    public Provider getProvider() {
        String json = getPrefs().getString(PROVIDER_INDEX, null);
        if (json == null) {
            return null;
        }
        Provider prov = null; //TODO convert from json
        return prov;
    }

    public boolean haveProvider() {
        return getPrefs().contains(PROVIDER_INDEX);
    }

    public void setProvider() {
        //TODO here get it as a string (json).
        String json = "";
        getPrefs().edit().putString(PROVIDER_INDEX, json).commit();
    }
    //</editor-fold>

    //<editor-fold desc="token">
    public String getToken() {
        return getPrefs().getString(TOKEN_INDEX, null);
    }

    public void setToken(String token) {
        getPrefs().edit().putString(TOKEN_INDEX, token).commit();
    }

    public boolean haveToken() {
        return getToken() != null;
    }
    //</editor-fold>
}
