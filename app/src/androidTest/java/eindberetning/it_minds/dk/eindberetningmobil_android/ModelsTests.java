package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.test.ApplicationTestCase;

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
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 02-07-2015.
 */

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
    }

    @Test
    public void testDrivingReport() throws MalformedURLException, JSONException {
        DrivingReport report = new DrivingReport("", "", "", "", false, false, true, new DateTime(), new DateTime(), 200.0d);
        assertTrue(DrivingReport.parseFromJson(report.saveToJson()).equals(report));
        assertTrue(DrivingReport.parseFromJson(report.saveToJson()).hashCode() == (report.hashCode()));
    }

    @Test
    public void testProfile() throws MalformedURLException, JSONException {
        Profile prof = new Profile(0, "", "", "", "", new ArrayList<Employments>(), new ArrayList<Tokens>());
        Profile prof2 = Profile.parseFromJson(prof.saveToJson());
        assertEquals(prof, prof2);
        MainSettings.getInstance(mContext).setProfile(prof);
        assertTrue(MainSettings.getInstance(mContext).getProfile().equals(prof));
        assertTrue(MainSettings.getInstance(mContext).getProfile().hashCode() == (prof.hashCode()));
    }

    @Test
    public void testTokens() {
        Tokens magic = new Tokens("gui-d", "magic", 1);
        MainSettings.getInstance(mContext).setToken(magic);
        assertTrue(MainSettings.getInstance(mContext).getToken().equals(magic));
        assertTrue(MainSettings.getInstance(mContext).getToken().hashCode() == magic.hashCode());

    }

    @Test
    public void testEmployments() throws MalformedURLException, JSONException {
        Employments emp = new Employments(0, "swagger");
        Employments emp2 = Employments.parseFromJson(emp.saveToJson());
        assertTrue(emp.equals(emp2));
        assertTrue(emp.hashCode() == emp2.hashCode());
    }

    @Test
    public void testGpsModel() throws MalformedURLException, JSONException {
        GPSCoordinateModel gps = new GPSCoordinateModel("", "");
        assertNotNull(gps.saveToJson());
        GPSCoordinateModel model2 = GPSCoordinateModel.parseFromJson(gps.saveToJson());
        assertTrue(model2.hashCode() == gps.hashCode());
        assertTrue(model2.equals(gps));
    }
}
