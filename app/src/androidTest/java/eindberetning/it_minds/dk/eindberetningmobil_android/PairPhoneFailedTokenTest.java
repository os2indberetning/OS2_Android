package eindberetning.it_minds.dk.eindberetningmobil_android;

import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
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

    public void testShowingSpinner() {
        solo.waitForDialogToClose();
    }

}
