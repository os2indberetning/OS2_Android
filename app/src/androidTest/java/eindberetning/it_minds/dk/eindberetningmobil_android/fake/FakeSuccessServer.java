/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android.fake;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import eindberetning.it_minds.dk.eindberetningmobil_android.data.StaticData;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerInterface;

public class FakeSuccessServer implements ServerInterface {
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
    public void pairPhone(String pairCode, ResultCallback<UserInfo> callback) {
        callback.onSuccess(StaticData.createSimpleUserInfo());
    }

    @Override
    public void sendReport(SaveableDriveReport report, ResultCallback<UserInfo> callback) {
        callback.onSuccess(StaticData.createSimpleUserInfo());
    }

    @Override
    public void sendReport(DriveReport report, ResultCallback<UserInfo> callback) {
        callback.onSuccess(StaticData.createSimpleUserInfo());
    }

    @Override
    public void validateToken(Tokens currentToken, ResultCallback<UserInfo> callback) {
        callback.onSuccess(StaticData.createSimpleUserInfo());
    }

    @Override
    public void getProviders(ResultCallback<List<Provider>> callback) {
        callback.onSuccess(new ArrayList<Provider>());
    }
}
