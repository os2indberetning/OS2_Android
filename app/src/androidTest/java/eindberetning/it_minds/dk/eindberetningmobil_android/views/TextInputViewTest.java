package eindberetning.it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.widget.EditText;

import org.junit.Test;

import eindberetning.it_minds.dk.eindberetningmobil_android.BaseTest;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.views.input.TextInputView;

public class TextInputViewTest extends BaseTest<TextInputView> {

    public TextInputViewTest() {
        super(TextInputView.class);
    }


    @Test
    public void testView() {
        solo.clickOnView(solo.getView(R.id.simple_text_input_view_field));
        EditText mainContainer = (EditText) solo.getView(R.id.simple_text_input_view_field);
        solo.clearEditText(mainContainer);
        solo.enterText(mainContainer, "testmig");
        assertEquals(mainContainer.getText().toString(), "testmig");
        solo.clickOnActionBarHomeButton();
        solo.waitForEmptyActivityStack(5000);
    }

    @Test
    public void testStartArgs() {
        solo.clickOnView(solo.getView(R.id.simple_text_input_view_field));
        EditText mainContainer = (EditText) solo.getView(R.id.simple_text_input_view_field);
        assertEquals(mainContainer.getText().toString(), "swag");
        solo.clickOnActionBarHomeButton();
        solo.waitForEmptyActivityStack(5000);
    }

    @Test
    public void testBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
        solo.waitForEmptyActivityStack(5000);

    }

    @Override
    public void runBeforeGetActivity() {
        Intent intent = new Intent();
        intent.putExtra(IntentIndexes.DATA_INDEX, "swag");
        intent.putExtra(IntentIndexes.TITLE_INDEX, "title");
        setActivityIntent(intent);
    }
}
