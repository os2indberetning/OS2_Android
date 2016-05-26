/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;

import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public abstract class BaseTest<T extends Activity> extends ActivityInstrumentationTestCase2<T> {
    public Solo solo;

    public BaseTest(Class<T> activityClass) {
        super(activityClass);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        runBeforeGetActivity();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public abstract void runBeforeGetActivity();

    public MainSettings getSettings() {
        return MainSettings.getInstance(getInstrumentation().getTargetContext());
    }


}
