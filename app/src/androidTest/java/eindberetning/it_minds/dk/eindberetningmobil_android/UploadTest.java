package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.data.StaticData;
import eindberetning.it_minds.dk.eindberetningmobil_android.fake.FakeSuccessServer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.StartActivity;
import it_minds.dk.eindberetningmobil_android.views.UploadingView;

/**
 * Created by kasper on 06-09-2015.
 */
public class UploadTest extends BaseTest<UploadingView> {

    @Override
    public void runBeforeGetActivity() {
        ServerFactory.setServerIface(new FakeSuccessServer());
        Intent report = new Intent();
        report.putExtra(IntentIndexes.DATA_INDEX, StaticData.createSimpleDrivingReport());
        setActivityIntent(report);
        MainSettings.getInstance(getActivity()).setProfile(StaticData.createSimpleProfile());
        MainSettings.getInstance(getActivity()).setToken(StaticData.createSimpleToken());
    }

    public UploadTest() {
        super(UploadingView.class);
    }


    @Test
    public void testIt() {
        solo.waitForActivity(StartActivity.class);
        ServerFactory.resetToDefault(getActivity());
    }


}
