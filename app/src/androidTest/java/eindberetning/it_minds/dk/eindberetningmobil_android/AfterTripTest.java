package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;

import org.joda.time.DateTime;
import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.views.AfterTripActivity;

/**
 * Created by kasper on 18-07-2015.
 */
public class AfterTripTest extends BaseTest<AfterTripActivity> {
    public AfterTripTest(){
        super(AfterTripActivity.class);
    }

    @Test
    public void testLayout(){
        solo.clickOnView(solo.getView(R.id.after_tracking_view_ended_at_home));
        solo.clickOnView(solo.getView(R.id.after_tracking_view_start_at_home));
        solo.clickOnView(solo.getView(R.id.after_tracking_view_cancel_btn));
    }
    @Test
    public void testEntries(){
        solo.clickOnView(solo.getView(R.id.after_tracking_view_km_container));
        solo.clickOnActionBarHomeButton();

    }

    @Override
    public void runBeforeGetActivity() {
        DrivingReport report = new DrivingReport("","","","",false, false, true, new DateTime(),new DateTime(),200.0d);
        Intent i = new Intent();
        i.putExtra(IntentIndexes.DATA_INDEX, report);
        setActivityIntent(i);
    }
}
