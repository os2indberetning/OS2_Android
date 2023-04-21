/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android.service;

import android.location.Location;
import android.test.ApplicationTestCase;

import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.service.MonitoringService;
import it_minds.dk.eindberetningmobil_android.service.MonitoringServiceReport;

public class MonitoringServiceTest extends ApplicationTestCase<MainApplication> {

    public MonitoringServiceTest() {
        super(MainApplication.class);
    }

    @Test
    public void testAll() {
        MonitoringServiceReport toTest = new MonitoringServiceReport(null, new MonitoringService());
        testEmptyPause(toTest);
        testAdd(toTest);
        testPause(toTest);
        testResume(toTest);
    }

    private void testEmptyPause(MonitoringServiceReport toTest) {
        toTest.pause();
        toTest.pause();
        testAdd(toTest);
    }


    private void testResume(MonitoringServiceReport toTest) {
        toTest.pause();
        testAdd(toTest);
    }

    private void testPause(MonitoringServiceReport toTest) {
        toTest.pause();
        testAdd(toTest);
    }

    private void testAdd(MonitoringServiceReport report) {
        Location loc = new Location("dummyprovider");
        loc.setLatitude(20.3);
        loc.setLongitude(52.6);
        report.addLocation(loc);
    }
}
