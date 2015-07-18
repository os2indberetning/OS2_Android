package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;


import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.ChooseProvider;

/**
 * Created by kasper on 18-07-2015.
 */
public class ChooseProviderTest extends ActivityInstrumentationTestCase2<ChooseProvider> {
    private Solo solo;

    public ChooseProviderTest() {
        super(ChooseProvider.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        solo = new Solo(getInstrumentation(), getActivity());
        MainSettings.getInstance(getActivity()).clear();
    }

    @Test
    public void testAll() throws Exception {
        solo.waitForText("Favrskov");
        solo.clickInList(0);

        solo.waitForView(R.id.pair_phone_view_pair_field);
        EditText codeField = (EditText) solo.getView(R.id.pair_phone_view_pair_field);
        solo.enterText(codeField, "wrongCode101");
        solo.clickOnView(solo.getView(R.id.pair_phone_view_pair_btn));
    }
}
