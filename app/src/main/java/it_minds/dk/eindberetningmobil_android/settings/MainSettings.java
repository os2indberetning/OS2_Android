/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.settings;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Purpose;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.internal.PrefilledData;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;

/**
 * handles all storage on the device, though sharedPrefs (a private one).
 */
public class MainSettings {


    //<editor-fold desc="Constants / indexes">
    private static final String PREF_NAME = "settings";
    private static final String PROVIDER_INDEX = "PROVIDER_INDEX";
    private static final String TOKEN_INDEX = "TOKEN_INDEX";
    private static final String RATES_INDEX = "RATES_INDEX";
    private static final String PURPOSE_INDEX = "PURPOSE_INDEX";
    private static final String PROFILES_INDEX = "PROFILES_INDEX";
    private static final String SERVICE_INDEX = "SERVICE_INDEX";
    private static final String AUTH_INDEX = "AUTH_INDEX";
    private static final String SAVED_REPORTS_INDEX = "SAVED_REPORTS_INDEX";
    private static final String PREFILLEDDATA_INDEX = "PREFILLEDDATA_INDEX";
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
     * haveProvider
     *
     * @return boolean
     */
    public boolean haveProvider() {
        return getPrefs().getString(PROVIDER_INDEX, null) != null;
    }

    /**
     * getProvider
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
     * setProvider
     *
     * @return void
     */
    public void setProvider(Provider newVal) {
        String json = null;
        if (newVal != null) {
            json = newVal.saveToJson().toString();
        }
        getPrefs().edit().putString(PROVIDER_INDEX, json).commit();
    }
    //</editor-fold>

    //<editor-fold desc="token">

    /**
     * haveToken
     *
     * @return boolean
     */
    public boolean haveToken() {
        return getPrefs().getString(TOKEN_INDEX, null) != null;
    }

    //</editor-fold>

    //<editor-fold desc="rates">
    public void setRates(ArrayList<Rates> rates) {

        //Remove rates that are not used on mobile reporting
        ArrayList<Rates> ratesToSave = new ArrayList<>();
        for (Rates r:rates){
            if (!r.getDescription().equalsIgnoreCase("Anhænger")){
                ratesToSave.add(r);
            }
        }

        JSONArray arr = Rates.saveAllToJson(ratesToSave);
        getPrefs().edit().putString(RATES_INDEX, arr.toString()).commit();
    }

    public ArrayList<Rates> getRates() {
        String val = getPrefs().getString(RATES_INDEX, null);
        if (val != null) {
            try {
                return Rates.parseAllFromJson(new JSONArray(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //Purpose data
    public void setPurpose(ArrayList<Purpose> purpose) {

        ArrayList<Purpose> purposeToSave = new ArrayList<>();
        for (Purpose p:purpose){
                purposeToSave.add(p);
            }

        JSONArray arr = Purpose.saveAllToJson(purposeToSave);
        getPrefs().edit().putString(PURPOSE_INDEX, arr.toString()).commit();
    }

    public ArrayList<Purpose> getPurpose() {
        String val = getPrefs().getString(PURPOSE_INDEX, null);
        if (val != null) {
            try {
                return Purpose.parseAllFromJson(new JSONArray(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Profile getProfile() {
        String val = getPrefs().getString(PROFILES_INDEX, null);
        if (val != null) {
            try {
                return Profile.parseFromJson(new JSONObject(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void clearProfile() {
        getPrefs().edit().remove(PROFILES_INDEX).commit();
    }

    public void setProfile(Profile profile) {
        getPrefs().edit().putString(PROFILES_INDEX, profile.saveToJson().toString()).commit();
    }

    public void clear() {
        getPrefs().edit().clear().commit();
    }

    /**
     * Clears all local stored EXCEPT purposes!
     */
    public void logoutClear(){
        ArrayList<Purpose> purposes = getPurpose();

        getPrefs().edit().clear().commit();

        setPurpose(purposes);
    }

    public void setServiceClosed(boolean serviceClosed) {
        getPrefs().edit().putBoolean(SERVICE_INDEX, serviceClosed).commit();
    }

    public boolean getServiceClosed() {
        return getPrefs().getBoolean(SERVICE_INDEX, true);
    }

    public void clearToken() {
        getPrefs().edit().remove(TOKEN_INDEX).commit();
    }
    //</editor-fold>

    //drive report saving

    public void addReport(SaveableReport report) {
        List<SaveableReport> drivingReports = getDrivingReports();
        drivingReports.add(report);
        setSavedReports(drivingReports);
    }

    public List<SaveableReport> getDrivingReports() {
        List<SaveableReport> reports = new ArrayList<>();
        String json = getPrefs().getString(SAVED_REPORTS_INDEX, null);
        if (json == null) {
            return reports;
        }
        //try parse.
        try {
            JSONArray arr = new JSONArray(json);
            reports = SaveableReport.parseAllFromJson(arr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return reports;
    }


    public void removeSavedReport(SaveableReport report) {
        List<SaveableReport> drivingReports = getDrivingReports();
        drivingReports.remove(report);
        setSavedReports(drivingReports);
    }

    private void setSavedReports(List<SaveableReport> reports) {
        //save to json
        String json = "";
        if (reports != null) {
            ArrayList<JSONObject> lst = new ArrayList<>();
            for (SaveableReport repo : reports) {
                lst.add(repo.saveToJson());
            }
            json = new JSONArray(lst).toString();
        }
        getPrefs().edit().putString(SAVED_REPORTS_INDEX, json).commit();
    }

    public void clearReports() {
        getPrefs().edit().remove(SAVED_REPORTS_INDEX).commit();
    }


    /**
     * havePrefilledData description here
     *
     * @return boolean
     */
    public boolean havePrefilledData() {
        return getPrefs().getString(PREFILLEDDATA_INDEX, null) != null;
    }

    /**
     * setPrefilledData description here
     *
     * @return void
     */
    public void setPrefilledData(PrefilledData newVal) {
        getPrefs().edit().putString(PREFILLEDDATA_INDEX, newVal.saveToJson().toString()).commit();
    }

    /**
     * getPrefilledData description here
     *
     * @return PrefilledData
     */
    public PrefilledData getPrefilledData() {
        String val = getPrefs().getString(PREFILLEDDATA_INDEX, null);
        if (val == null) {
            return null;
        }
        try {
            return PrefilledData.parseFromJson(new JSONObject(val));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
