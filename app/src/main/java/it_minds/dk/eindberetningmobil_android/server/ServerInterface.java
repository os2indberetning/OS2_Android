/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.server;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;

public interface ServerInterface {
    String getBaseUrl();

    void setBaseUrl(String baseUrl);

    ImageLoader getImageLoader();

    void pairPhone(String pairCode, final ResultCallback<UserInfo> callback);

    void sendReport(SaveableDriveReport report, ResultCallback<UserInfo> callback);

    void sendReport(DriveReport report, ResultCallback<UserInfo> callback);

    void validateToken(Tokens currentToken, ResultCallback<UserInfo> callback);

    void getProviders(final ResultCallback<List<Provider>> callback);
}
