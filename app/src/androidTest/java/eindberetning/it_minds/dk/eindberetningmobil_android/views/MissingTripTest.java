package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.widget.ListView;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.MissingTripActivity;

public class MissingTripTest extends BaseTest<MissingTripActivity> {
    public MissingTripTest() {
        super(MissingTripActivity.class);
    }


    @Override
    public void runBeforeGetActivity() {
        MainSettings.getInstance(getActivity()).clearReports();
        MainSettings.getInstance(getActivity()).addReport(new SaveableReport("", "", "", 2000, null));
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().refreshData();
            }
        });
    }

    @Test
    public void testVisable() {
        solo.waitForView(R.id.missing_trips_listview);
        ListView lw = (ListView) solo.getView(R.id.missing_trips_listview);
        assertTrue(lw.getAdapter().getCount() == 1);
    }

    @Test
    public void testDelete() throws InterruptedException {
        solo.waitForView(R.id.missing_trips_listview);
        ListView lw = (ListView) solo.getView(R.id.missing_trips_listview);
        int sizeBefore = lw.getAdapter().getCount();
        solo.clickInList(0);
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_no));
        solo.waitForDialogToClose();
        solo.waitForView(R.id.missing_trips_listview);
        assertTrue(lw.getAdapter().getCount() == sizeBefore - 1);
    }

    @Test
    public void testSend() {

        solo.waitForView(R.id.missing_trips_listview);
        //ListView lw = (ListView) solo.getView(R.id.missing_trips_listview);
        solo.clickInList(0);
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
        solo.waitForDialogToClose();

    }


}
