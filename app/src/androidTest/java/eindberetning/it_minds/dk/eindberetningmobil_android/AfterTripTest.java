package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.AfterTripActivity;
import it_minds.dk.eindberetningmobil_android.views.input.TextInputView;
import it_minds.dk.eindberetningmobil_android.views.input.RateActivity;

/**
 * Created by kasper on 18-07-2015.
 */
public class AfterTripTest extends BaseTest<AfterTripActivity> {
    public AfterTripTest() {
        super(AfterTripActivity.class);
    }

    @Test
    public void testLayout() {
        solo.clickOnView(solo.getView(R.id.after_tracking_view_ended_at_home));
        solo.clickOnView(solo.getView(R.id.after_tracking_view_start_at_home));
        solo.clickOnView(solo.getView(R.id.after_tracking_view_cancel_btn));
    }

    @Test
    public void testEntries() throws InterruptedException {
        solo.clickOnView(solo.getView(R.id.after_tracking_view_km_container));
        solo.waitForActivity(TextInputView.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));
        solo.clickOnView(solo.getView(R.id.after_tracking_view_extra_desc));
        solo.waitForActivity(TextInputView.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));
//
        solo.clickOnView(solo.getView(R.id.after_tracking_view_purpose_desc));
        solo.waitForActivity(TextInputView.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));
//
        solo.clickOnView(solo.getView(R.id.after_tracking_view_rate_desc));
        solo.waitForActivity(RateActivity.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));

        solo.clickOnView(solo.getView(R.id.after_tracking_view_org_location_desc));
        solo.waitForActivity(TextInputView.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));

        solo.clickOnView(solo.getView(R.id.after_tracking_view_km_container));
        solo.waitForActivity(TextInputView.class);
        solo.clickOnActionBarHomeButton();
        solo.waitForView(solo.getView(R.id.after_tracking_view_km_container));
    }


    @Override
    public void runBeforeGetActivity() {

        DrivingReport report = new DrivingReport("", "", "", "", false, false, true, new DateTime(), new DateTime(), 200.0d);
        Intent i = new Intent();
        i.putExtra(IntentIndexes.DATA_INDEX, report);
        setActivityIntent(i);
        ArrayList<Rates> rates = new ArrayList<>();
        rates.add(new Rates(0,"swagging", "2051"));
        MainSettings.getInstance(getActivity()).setRates(rates);
    }

    @Test
    public void testNavigateBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
    }
}
