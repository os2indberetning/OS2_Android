package eindberetning.it_minds.dk.eindberetningmobil_android;

import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.views.StartActivity;

/**
 * Created by kasper on 18-07-2015.
 */
public class StartActivityTest  extends BaseTest<StartActivity>{
    public StartActivityTest() {
        super(StartActivity.class);
    }

    @Test
    public void testFields(){
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_start_at_home));
        solo.clickOnView(solo.getView(R.id.start_tracking_layout_start_btn));
    }

    @Override
    public void runBeforeGetActivity() {

    }
}
