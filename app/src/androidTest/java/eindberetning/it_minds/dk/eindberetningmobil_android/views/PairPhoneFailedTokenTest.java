package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import org.junit.Test;

import java.util.ArrayList;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.ChooseProvider;
import it_minds.dk.eindberetningmobil_android.views.PairPhone;

/**
 * Created by kasper on 26-07-2015.
 */
public class PairPhoneFailedTokenTest extends BaseTest<PairPhone> {
    public PairPhoneFailedTokenTest() {
        super(PairPhone.class);
    }


    @Override
    public void runBeforeGetActivity() {
        MainSettings.getInstance(getActivity()).clear();
        MainSettings.getInstance(getActivity()).setToken(new Tokens("", "", 1));
    }

    @Test
    public void testEnteringToken() {
        solo.waitForView(solo.getView(R.id.pair_phone_view_pair_btn));
        solo.waitForDialogToOpen();
        solo.waitForDialogToClose();
        solo.enterText(solo.getEditText(0), "8353707760");
        solo.clickOnView(solo.getView(R.id.pair_phone_view_pair_btn));
        solo.waitForText("token");
    }

    @Test
    public void testSearchMethod() {
        ArrayList<Tokens> tokens = new ArrayList<>();
        tokens.add(new Tokens("asd", "789", 1));
        tokens.add(new Tokens("asd1", "1789", 1));
        tokens.add(new Tokens("asd2", "2789", 2));
        tokens.add(new Tokens("asd5", "3789", 1));
        tokens.add(new Tokens("asd5", "9000", 1));
        assertTrue(getActivity().findTokenInUserInfo(new UserInfo(new Profile(0, "", "", "", "", new ArrayList<Employments>(), tokens), new ArrayList<Rates>()), "9000"));
    }

    @Test
    public void testSearchMethodNoResult() {
        ArrayList<Tokens> tokens = new ArrayList<>();
        tokens.add(new Tokens("asd", "789", 1));
        tokens.add(new Tokens("asd1", "1789", 1));
        tokens.add(new Tokens("asd2", "2789", 2));
        tokens.add(new Tokens("asd5", "3789", 1));
        tokens.add(new Tokens("asd5", "9000", 1));
        assertFalse(getActivity().findTokenInUserInfo(new UserInfo(new Profile(0, "", "", "", "", new ArrayList<Employments>(), tokens), new ArrayList<Rates>()), "9001"));
    }

    @Test
    public void testBack(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
        solo.waitForActivity(ChooseProvider.class);
    }

}
