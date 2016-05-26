/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.data.StaticData;
import eindberetning.it_minds.dk.eindberetningmobil_android.fake.FakeSuccessServer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.StartActivity;
import it_minds.dk.eindberetningmobil_android.views.UploadingView;

public class UploadTest extends BaseTest<UploadingView> {

    @Override
    public void runBeforeGetActivity() {
        ServerFactory.setServerIface(new FakeSuccessServer());
        Intent report = new Intent();
        report.putExtra(IntentIndexes.DATA_INDEX, StaticData.createSimpleDrivingReport());
        setActivityIntent(report);
        MainSettings.getInstance(getActivity()).setProfile(StaticData.createSimpleProfile());
        MainSettings.getInstance(getActivity()).setProvider(new Provider("Test provider", "https://fake-url.com", "https://fake-url.com/image.jpg", "#000000", "#FFFF00", "#FF00FF"));
    }

    public UploadTest() {
        super(UploadingView.class);
    }


    @Test
    public void testIt() {
        solo.waitForActivity(StartActivity.class);
        ServerFactory.resetToDefault(getActivity());
    }


}
