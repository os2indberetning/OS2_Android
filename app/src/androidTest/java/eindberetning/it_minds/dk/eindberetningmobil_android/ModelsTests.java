package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.test.ApplicationTestCase;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Provider;

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
    }

    @Test
    public void testDrivingReport() throws MalformedURLException, JSONException {
        DrivingReport report = new DrivingReport("", "", "", "", false, false, true, new DateTime(), new DateTime(), 200.0d);
        assertEquals(DrivingReport.parseFromJson(report.saveToJson()), report);
    }

    @Test
    public void testProfile() throws MalformedURLException, JSONException {
        Profile prof = new Profile(0, "", "", "", "", null, null);
        Profile prof2 = Profile.parseFromJson(prof.saveToJson());
//        assertEquals(prof, prof2);
    }
}
