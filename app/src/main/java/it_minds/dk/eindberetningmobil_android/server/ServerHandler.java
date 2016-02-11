/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;

/**
 * the server api.
 * It utilizes the singleton pattern to save resources, but you can still new it, in case that is really requried.
 */
public class ServerHandler implements ServerInterface {

    private final static int RETRY_MS = 6*1000;
    private final static int RETRY_COUNT = 4;

    public static final String PROVIDER_URL = "https://ework.favrskov.dk/FavrskovMobilityAPI/api/AppInfo";
    //<editor-fold desc="Endpoint constants">
    private static final String SyncTokenEndpoint = "/SyncWithToken";
    private static final String getUserData = "/UserData";
    private static final String SubmitDrive = "/SubmitDrive";
    private static final RetryPolicy noRetryPolicy = new DefaultRetryPolicy(RETRY_MS, -1, 0);
    private final RetryPolicy defaultPolicy;

    //</editor-fold>

    //<editor-fold desc="variables">
    private final RequestQueue queue;



    //Based on the provider. So this gets updated in the beginning of the app launch.
    private String baseUrl;
    //</editor-fold>


    public ServerHandler(Context context) {
        queue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        defaultPolicy = new DefaultRetryPolicy(RETRY_MS, RETRY_COUNT, 1.5f); //6 secounds in the beginning.
    }

    //<editor-fold desc="Server requests / api">
    public void getProviders(final ResultCallback<List<Provider>> callback) {
        Request req = (new JsonArrayRequest(Request.Method.GET, PROVIDER_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    List<Provider> provs = Provider.parseAllFromJson(response);
                    callback.onSuccess(provs);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }));
        req.setRetryPolicy(defaultPolicy);
        queue.add(req);
    }

    /**
     * @param callback onsuccess if success, onError if failed
     */
    public void validateToken(Tokens currentToken, ResultCallback<UserInfo> callback) {
        if (currentToken == null) {
            callback.onError(new IllegalArgumentException("Token is null"));
            return;
        }
        String url = getBaseUrl() + getUserData;
        SafeJsonHelper json = new SafeJsonHelper();
        json.put("guid", currentToken.getGuId());
        makeRequestWithUserInfoCallback(callback, url, json, true);
    }

    /**
     * Used to send a Drive Report that has just been finished to the backend
     * @param report The report to send
     * @param callback The callback to use for onSucces / onError
     */
    public void sendReport(DriveReport report, ResultCallback<JSONObject> callback){
        if (report == null) {
            callback.onError(new IllegalArgumentException("Report is null"));
        } else {
            String url = getBaseUrl() + SubmitDrive;
            makeRequestWithStatusCallback(callback, url, report.saveAsJson(), false);
        }
    }

    /**
     * Used to send a Saved Drive Report to the backend
     * @param report The report to send
     * @param callback The callback to use for onSucces / onError
     */
    @Override
    public void sendSavedReport(SaveableDriveReport report, ResultCallback<JSONObject> callback) {
        if (report == null) {
            callback.onError(new IllegalArgumentException("Report is null"));
        } else {
            String url = getBaseUrl() + SubmitDrive;
            makeRequestWithStatusCallback(callback, url, report.saveAsJson(), false);
        }
    }

    /**
     * @param callback onsuccess if success, onError if failed
     */
    public void pairPhone(String pairCode, final ResultCallback<UserInfo> callback) {

        String url = getBaseUrl() + SyncTokenEndpoint;
        SafeJsonHelper json = new SafeJsonHelper();
        json.put("TokenString", pairCode);
        makeRequestWithUserInfoCallback(callback, url, json, true);

    }
    //</editor-fold>

    //<editor-fold desc="Helper function(s)">

    /**
     * Sends POST request with JSON body via Volley and returns a JSONObject to the supplied callback listener
     * or the volley error if the request failed.
     * @param callback the callback to be used for succes/error
     * @param url the url for the request
     * @param json the JSONObject to be parsed to the requests body
     * @param mayRetry whether the request can retry without user interaction
     */
    private void makeRequestWithStatusCallback(final ResultCallback<JSONObject> callback, String url, JSONObject json, boolean mayRetry){
        Log.d("DEBUG JSON", json.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        if (mayRetry) {
            request.setRetryPolicy(defaultPolicy);
        } else {
            request.setRetryPolicy(noRetryPolicy);
        }
        queue.add(request);
    }

    /**
     * Sends POST request with JSON body via Volley and returns a UserInfo object to the supplied callback listener
     * or returns the volley error if the request failed to the callbacks onError().
     * @param callback the callback (onsuccess if success, onError if failed)
     * @param url      the absolute address
     * @param json     the json to send.
     * @param mayRetry if we may retry the request.
     */
    private void makeRequestWithUserInfoCallback(final ResultCallback<UserInfo> callback, String url, JSONObject json, boolean mayRetry) {
        Log.d("DEBUG JSON",json.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    callback.onSuccess(UserInfo.parseFromJson(response));
                } catch (Exception e) {
                    Logger.getLogger("ServerHandler").log(Level.SEVERE, "", e);
                    callback.onError(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });
        if (mayRetry) {
            request.setRetryPolicy(defaultPolicy);
        } else {
            request.setRetryPolicy(noRetryPolicy);
        }
        queue.add(request);
    }
    //</editor-fold>


    //<editor-fold desc="handle base url methods">
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
    //</editor-fold>


    //<editor-fold desc="Singleton (serverhandler, and imageload)">
    private static ServerHandler instance;
    private ImageLoader imageLoader = null;

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(queue, new BitmapLruCache());
        }
        return imageLoader;
    }

    /**
     * singleton pattern. [only half, since you can for special reasons create a new instance].
     *
     * @return
     */
    public static ServerHandler getInstance(Context context) {
        if (instance == null) {
            instance = new ServerHandler(context);
        }
        return instance;
    }
    //</editor-fold>

}

