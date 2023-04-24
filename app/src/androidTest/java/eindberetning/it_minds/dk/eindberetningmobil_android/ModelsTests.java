/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package eindberetning.it_minds.dk.eindberetningmobil_android;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.GPSCoordinateModel;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Route;

public class ModelsTests extends ApplicationTestCase<MainApplication> {
    public ModelsTests() {
        super(MainApplication.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testProvider() throws Exception {
        Provider provider = new Provider("name", "api", "imageurl", "textcolor", "primaryColor", "secoundaryColor");
        assertTrue("property should be set", provider.getName().equals("name"));
        assertTrue("property should be set", provider.getAPIUrl().equals("api"));
        assertTrue("property should be set", provider.getImgUrl().equals("imageurl"));
        assertTrue("property should be set", provider.getTextColor().equals("textcolor"));
        assertTrue("property should be set", provider.getPrimaryColor().equals("primaryColor"));
        assertTrue("property should be set", provider.getSecondaryColor().equals("secoundaryColor"));
        assertTrue("should be able to serialize", provider.saveToJson() != null);
        Provider deserialized = Provider.parseFromJson(provider.saveToJson());
        assertTrue("should be able to deserialize itself", deserialized != null);
        assertTrue("hashcode should be reflective", deserialized.hashCode() == provider.hashCode());
        assertTrue(provider.equals(deserialized));

    }

    @Test
    public void testDrivingReport() throws MalformedURLException, JSONException {
        DateTime dt = new DateTime();
        DrivingReport report = new DrivingReport("", "1", "1", "", false, false, true, false, dt, dt, 200.0d, 200.0d);
        DrivingReport report2 = new DrivingReport("", "1", "1", "", false, false, true, false, dt, dt, 200.0d, 2000.0d);
        report.saveToJson(0); //indirect assertion, it can serialize.
        assertTrue(report.equals(report2));
        assertTrue(report.hashCode() == report2.hashCode());
    }

    @Test
    public void testEmployments() throws MalformedURLException, JSONException {
        Employments emp = new Employments(0, "swagger", "010", false);
        Employments emp2 = Employments.parseFromJson(emp.saveToJson());
        assertTrue(emp.equals(emp2));
        assertTrue(emp.hashCode() == emp2.hashCode());
    }

    @Test
    public void testGpsModel() throws MalformedURLException, JSONException {
        GPSCoordinateModel gps = new GPSCoordinateModel(0.0d, 0.0d);
        assertNotNull(gps.saveToJson());
        GPSCoordinateModel model2 = GPSCoordinateModel.parseFromJson(gps.saveToJson());
        assertTrue(model2.hashCode() == gps.hashCode());
        assertTrue(model2.equals(gps));
    }

    @Test
    public void testRoute() throws MalformedURLException, JSONException {
        Route route = new Route(1000.2d, new ArrayList<GPSCoordinateModel>());
        assertTrue(route.getTotalDistance() == 1000.2d);
        assertTrue(route.getGPSCoordinates().size() == 0);
        //assertTrue(route.getFourKmRuleDistance() == 11.1d);
        route.saveToJson();//indirect assert not throws
    }

}
