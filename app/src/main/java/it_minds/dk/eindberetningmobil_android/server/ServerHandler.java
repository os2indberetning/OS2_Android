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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
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
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * the server api.
 * It utilizes the singleton pattern to save resources, but you can still new it, in case that is really requried.
 */
public class ServerHandler implements ServerInterface {

    //Server config
    private final static int RETRY_MS = 6*1000;
    private final static int RETRY_COUNT = 4;

    public static final String PROVIDER_URL = "https://www.os2indberetning.dk/appinfo.json";
    private static final RetryPolicy noRetryPolicy = new DefaultRetryPolicy(RETRY_MS, -1, 0);
    private final RetryPolicy defaultPolicy;

    //BaseUrl is based on the provider chosen by the user
    private String baseUrl;

    //API v.2 endpoints (New login, new submit etc)
    private static final String loginEndpoint = "/auth";
    private static final String submitEndpoint = "/report";
    private static final String userInfoEndpoint = "/userinfo";

    private final RequestQueue queue;

    private Context context;

    public ServerHandler(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        defaultPolicy = new DefaultRetryPolicy(RETRY_MS, RETRY_COUNT, 1.5f); //6 secounds in the beginning.
    }

    //<editor-fold desc="Server requests / api">

    /**
     * Gets the OS2 providers that has enabled use of the app
     * @param callback
     */
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
     * Used to send a Drive Report that has just been finished to the backend
     * @param report The report to send
     * @param callback The callback to use for onSucces / onError
     */
    public void sendReport(DriveReport report, ResultCallback<JSONObject> callback){
        if (report == null) {
            callback.onError(new IllegalArgumentException("Report is null"));
        } else {
            String url = getBaseUrl() + submitEndpoint;
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
            String url = getBaseUrl() + submitEndpoint;
            makeRequestWithStatusCallback(callback, url, report.saveAsJson(), false);
        }
    }

    /**
     * Logs the user in with the given credentials
     * @param username
     * @param password
     * @param callback
     */
    @Override
    public void loginWithCredentials(String username, String password, ResultCallback<UserInfo> callback) {
        String url = getBaseUrl() + loginEndpoint;
        SafeJsonHelper json = new SafeJsonHelper();
        json.put("UserName", username);
        json.put("Password", password);
        makeRequestWithUserInfoCallback(callback, url, json, true);
    }

    /**
     * Used to check user is still valid in the backend
     * @param guId
     * @param callback
     */
    @Override
    public void syncUserInfo(JSONObject guId, ResultCallback<UserInfo> callback) {
        String url = getBaseUrl() + userInfoEndpoint;
        makeRequestWithUserInfoCallback(callback, url, guId, true);
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
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, json.toString(),
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        }){
            //Need to overwrite base method to force statusCode in response
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                SafeJsonHelper json = new SafeJsonHelper();
                if(response.statusCode == 200){
                    return Response.success(json.put("statusCode", response.statusCode),
                            HttpHeaderParser.parseCacheHeaders(response));
                }
                return super.parseNetworkResponse(response);
            }
        };

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
                    JSONObject res = response;
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
        if(baseUrl == null){
            baseUrl = MainSettings.getInstance(context).getProvider().getAPIUrl();
        }
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

