package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.widget.TextView;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import eindberetning.it_minds.dk.eindberetningmobil_android.data.StaticData;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.views.UploadingView;

public class UploadingViewTest extends BaseTest<UploadingView> {
    @Override
    public void runBeforeGetActivity() {
        getSettings().setProfile(StaticData.createSimpleProfile());
    }

    public UploadingViewTest() {
        super(UploadingView.class);
    }

    @Test
    public void testLayout() {
        solo.waitForView(R.id.uploading_view_image);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().updateStatusText("asd-swag");
            }
        });
        TextView view = (TextView) solo.getView(R.id.upload_view_status_text);
        assertEquals(view.getText(), "asd-swag");
    }

}
