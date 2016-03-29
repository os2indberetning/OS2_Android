package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.widget.EditText;

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
        solo.waitForView(R.id.user_login_username);
        EditText usernameField = (EditText) solo.getView(R.id.user_login_username);
        EditText passwordField = (EditText) solo.getView(R.id.user_login_password);
        solo.enterText(usernameField, "wrongUserName");
        solo.enterText(passwordField, "wrongCode101");
        solo.clickOnView(solo.getView(R.id.user_login_button));
    }

    @Override
    public void runBeforeGetActivity() {
        MainSettings.getInstance(getActivity()).clear();
    }
}
