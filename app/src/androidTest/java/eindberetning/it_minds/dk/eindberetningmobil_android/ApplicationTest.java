package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.app.Application;
import android.test.ApplicationTestCase;

import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.models.Token;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testTest() {
//        throw new RuntimeException("i work");
        Token token = new Token("1", "2", "3");
        assertTrue(token.getguid().equals("1"));

    }
    public void testProviderTest(){
        Provider prov = new Provider("myname", "","","","","");
        assertTrue(prov.getName().equals("myname"));
    }
}