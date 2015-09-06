package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;


import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.ChooseProvider;

/**
 * Created by kasper on 18-07-2015.
 */
public class ChooseProviderTest extends BaseTest<ChooseProvider> {
    public ChooseProviderTest() {
        super(ChooseProvider.class);
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

    @Override
    public void runBeforeGetActivity() {
        MainSettings.getInstance(getActivity()).clear();
    }
}
