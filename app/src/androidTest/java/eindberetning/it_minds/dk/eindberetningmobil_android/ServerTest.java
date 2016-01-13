/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.test.ApplicationTestCase;

import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;

public class ServerTest extends ApplicationTestCase<MainApplication> {
    public ServerTest() {
        super(MainApplication.class);
    }

    @Test
    public void testValidate(){
        ServerFactory.resetToDefault(mContext);
        ServerFactory.getInstance(mContext).setBaseUrl("https://ework.favrskov.dk/FavrskovMobilityAPI/api/");
        ServerFactory.getInstance(mContext).validateToken(new Tokens("", "", 1), new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {

            }

            @Override
            public void onError(Exception error) {

            }
        });
        ServerFactory.getInstance(mContext).validateToken(null, new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                assertTrue("should call error case",false );
            }

            @Override
            public void onError(Exception error) {

            }
        });


    }
}
