package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.test.ApplicationTestCase;

import it_minds.dk.eindberetningmobil_android.MainApplication;
import it_minds.dk.eindberetningmobil_android.models.Provider;

/**
 * Created by kasper on 02-07-2015.
 */
public class ModelsTests extends ApplicationTestCase<MainApplication> {
    public ModelsTests(){
        super(MainApplication.class);
    }

    public void testName() throws Exception {
    }

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
        assertTrue("should be able to deserialize itself", deserialized  != null);


    }
}
