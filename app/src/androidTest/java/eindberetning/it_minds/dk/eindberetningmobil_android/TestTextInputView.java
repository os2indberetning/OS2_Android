package eindberetning.it_minds.dk.eindberetningmobil_android;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Test;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.views.TextInputView;

/**
 * Created by kasper on 18-07-2015.
 */
public class TestTextInputView extends BaseTest<TextInputView> {

    public TestTextInputView() {
        super(TextInputView.class);
    }


    @Test
    public void testView() {
        solo.clickOnView(solo.getView(R.id.simple_text_input_view_field));
        EditText mainContainer = (EditText) solo.getView(R.id.simple_text_input_view_field);
        solo.clearEditText(mainContainer);
        solo.enterText(mainContainer, "testmig");
        assertEquals(mainContainer.getText().toString(), "testmig");
    }

    @Test
    public void testStartArgs() {
        solo.clickOnView(solo.getView(R.id.simple_text_input_view_field));
        EditText mainContainer = (EditText) solo.getView(R.id.simple_text_input_view_field);
        assertEquals(mainContainer.getText().toString(), "swag");
    }

    @Test
    public void testBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });

    }

    @Override
    public void runBeforeGetActivity() {
        Intent intent = new Intent();
        intent.putExtra(IntentIndexes.DATA_INDEX, "swag");
        intent.putExtra(IntentIndexes.TITLE_INDEX, "title");
        setActivityIntent(intent);
    }
}
