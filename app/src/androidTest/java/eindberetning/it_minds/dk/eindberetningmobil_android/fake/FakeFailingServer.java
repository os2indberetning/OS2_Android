/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android.fake;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerInterface;
import it_minds.dk.eindberetningmobil_android.views.UploadingView;

public class FakeFailingServer implements ServerInterface {


    private ActivityInstrumentationTestCase2 tester;
    private Activity mainActivity;

    public FakeFailingServer() {

    }

    public FakeFailingServer(ActivityInstrumentationTestCase2 testCase) {
        this.tester = testCase;
    }

    public FakeFailingServer(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private Exception toSendBack;

    private Timer innerTime = new Timer();
    private int callbackTimeInMs = 1000;

    public void setException(Exception e) {
        this.toSendBack = e;
    }

    @Override
    public String getBaseUrl() {
        return null;
    }

    @Override
    public void setBaseUrl(String baseUrl) {

    }

    @Override
    public ImageLoader getImageLoader() {
        return null;
    }

    @Override
    public void pairPhone(String pairCode, final ResultCallback<UserInfo> callback) {
        handleCallback(callback);
    }

    @Override
    public void sendReport(DriveReport report, ResultCallback<JSONObject> callback) {
        handleCallback(callback);
    }

    @Override
    public void sendSavedReport(SaveableDriveReport report, ResultCallback<JSONObject> callback) {
        handleCallback(callback);
    }

    private void handleCallback(final ResultCallback callback) {
        final Runnable toRun = new Runnable() {
            @Override
            public void run() {
                callback.onError(toSendBack);
            }
        };
        innerTime.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mainActivity != null) {
                    mainActivity.runOnUiThread(toRun);
                } else if (tester != null) {
                    try {
                        tester.runTestOnUiThread(toRun);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }
        }, callbackTimeInMs);
    }

    @Override
    public void validateToken(Tokens currentToken, ResultCallback<UserInfo> callback) {
        handleCallback(callback);
    }

    @Override
    public void getProviders(ResultCallback<List<Provider>> callback) {
        handleCallback(callback);
    }

    public void setDefaultError() {
        setException(new Exception("test exception"));
    }

    public void setActivity(UploadingView activity) {
        this.mainActivity = activity;
    }
}
