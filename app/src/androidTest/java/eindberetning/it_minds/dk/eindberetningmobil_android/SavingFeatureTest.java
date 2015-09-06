package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;
import android.widget.ListView;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.data.StaticData;
import eindberetning.it_minds.dk.eindberetningmobil_android.fake.FakeFailingServer;
import eindberetning.it_minds.dk.eindberetningmobil_android.fake.FakeSuccessServer;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.views.StartActivity;
import it_minds.dk.eindberetningmobil_android.views.UploadingView;

/**
 * Created by kasper on 06-09-2015.
 */
public class SavingFeatureTest extends BaseTest<UploadingView> {

    public SavingFeatureTest() {
        super(UploadingView.class);
    }


    @Override
    public void runBeforeGetActivity() {
        getSettings().clear();
        getSettings().setProfile(StaticData.createSimpleProfile());
        getSettings().setToken(StaticData.createSimpleToken());
        Intent report = new Intent();
        report.putExtra(IntentIndexes.DATA_INDEX, StaticData.createSimpleDrivingReport());
        FakeFailingServer fakeFailingServer = new FakeFailingServer(this);
        ServerFactory.setServerIface(fakeFailingServer);
        setActivityIntent(report);

    }

//    @Test void


    @Test
    public void testSave() throws InterruptedException {
        solo.waitForDialogToOpen();
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_no));//no.
        solo.waitForActivity(StartActivity.class);

        solo.pressMenuItem(0);
        solo.waitForView(solo.getView(R.id.missing_trips_listview));

        ListView view = (ListView) solo.getView(R.id.missing_trips_listview);
        int count = view.getCount();
        assertTrue("Should only save one report.", count == 1);
        solo.clickInList(0);
        solo.waitForDialogToOpen();
        ServerFactory.setServerIface(new FakeSuccessServer());
        solo.clickOnView(solo.getView(R.id.confirmation_end_driving_dialog_ok));
        solo.waitForText(solo.getString(R.string.send_and_recived));
        ServerFactory.getInstance(getActivity());
    }
}
