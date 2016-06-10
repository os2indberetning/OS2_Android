package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.joda.time.DateTime;
import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.views.AfterTripActivity;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

public class MonitoringTest extends BaseTest<MonitoringActivity> {
    @Override
    public void runBeforeGetActivity() {
        Intent i = new Intent();
        DrivingReport report = new DrivingReport("", "", "", "", false, false, false, false, new DateTime(), new DateTime(), 0);
        i.putExtra(IntentIndexes.DATA_INDEX, report);
        setActivityIntent(i);
    }

    public MonitoringTest() {
        super(MonitoringActivity.class);
    }

    @Test
    public void testDialog() throws InterruptedException {
        solo.clickOnView(solo.getView(R.id.monitoring_view_stop_btn));
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_no));
        solo.clickOnView(solo.getView(R.id.monitoring_view_pause_resume_btn));
        solo.clickOnView(solo.getView(R.id.monitoring_view_pause_resume_btn));
        solo.clickOnView(solo.getView(R.id.monitoring_view_stop_btn));
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
        solo.waitForActivity(AfterTripActivity.class);
    }

    @Test
    public void testCommandDistanceError() {
        final Bundle b = new Bundle();
        b.putBoolean(IntentIndexes.ERROR_INDEX, true);
        sendCommand(b);
        waitForDialogAndAccept();

    }
    @Test
    public void testCommandGPSError() {
        final Bundle b = new Bundle();
        b.putBoolean(IntentIndexes.ERROR_GPS_INDEX, true);
        sendCommand(b);
        waitForDialogAndAccept();

    }
    @Test
    public void testCommandGpsErrorWorking() {
        final Bundle b = new Bundle();
        b.putBoolean(IntentIndexes.WORKING_GPS_INDEX, true);
        sendCommand(b);
    }

    private void waitForDialogAndAccept() {
        solo.waitForDialogToOpen();
        solo.waitForView(R.id.confirmation_end_driving_dialog_ok);
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
    }

    private void sendCommand(final Bundle b) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().getMainReciver().onReceiveResult(Activity.RESULT_OK, b);
            }
        });
    }
}
