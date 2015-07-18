package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Before;

import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 18-07-2015.
 */
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


}
