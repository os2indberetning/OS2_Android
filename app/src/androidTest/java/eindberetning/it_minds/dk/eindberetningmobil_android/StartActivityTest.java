package eindberetning.it_minds.dk.eindberetningmobil_android;

import org.junit.Test;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.ChooseProvider;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;
import it_minds.dk.eindberetningmobil_android.views.StartActivity;
import it_minds.dk.eindberetningmobil_android.views.input.EmployementActivity;
import it_minds.dk.eindberetningmobil_android.views.input.RateActivity;
import it_minds.dk.eindberetningmobil_android.views.input.TextInputView;

/**
 * Created by kasper on 18-07-2015.
 */
public class StartActivityTest extends BaseTest<StartActivity> {
    public StartActivityTest() {
        super(StartActivity.class);
    }

    @Test
    public void testFields() {
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_start_at_home));
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_start_btn));
    }

    @Test
    public void testStart() {
        ArrayList<Employments> employess = new ArrayList<Employments>();
        employess.add(new Employments(999, "tester"));
        ArrayList<Tokens> tokenses = new ArrayList<>();
        tokenses.add(new Tokens("", "", 1));
        MainSettings.getInstance(getActivity()).setProfile(new Profile(0, "", "", "", "", employess, tokenses));
        ArrayList<Rates> rates = new ArrayList<>();
        rates.add(new Rates(6667,"rate descrition","no year"));
        MainSettings.getInstance(getActivity()).setRates(rates);
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_purpose));

        solo.waitForActivity(TextInputView.class);
        solo.enterText(solo.getEditText(0), "test itminds");
        solo.clickOnActionBarHomeButton();
        solo.waitForActivity(StartActivity.class);

        solo.clickOnView(solo.getView(R.id.start_tracking_layout_org_location));
        solo.waitForActivity(EmployementActivity.class);
        solo.clickInList(0);

        solo.waitForActivity(StartActivity.class);

        solo.clickOnView(solo.getView(R.id.start_tracking_layout_rate));
        solo.waitForActivity(RateActivity.class);
        solo.clickInList(0);

        solo.waitForActivity(StartActivity.class);
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_start_btn));
        solo.waitForActivity(MonitoringActivity.class);
    }


    @Override
    public void runBeforeGetActivity() {
    }

    @Test
    public void testBackWithData() {
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_purpose));

        solo.waitForActivity(TextInputView.class);
        solo.enterText(solo.getEditText(0), "test itminds");
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.start_tracking_layout_start_btn));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
    }

    @Test
    public void testBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
        solo.waitForEmptyActivityStack(5000);
    }
}
