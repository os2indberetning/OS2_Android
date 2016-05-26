package it_minds.dk.eindberetningmobil_android.views.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;

/**
 * Fullscreen text input
 */
public class TextInputView extends ProvidedSimpleActivity {
    private EditText field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_text_input_view);
        field = getViewById(R.id.simple_text_input_view_field);
        field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });
        String providedText = getIntent().getStringExtra(IntentIndexes.DATA_INDEX);
        if (providedText != null) {
            field.setText(providedText);
        }
        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);
        if (title == null) {
            title = "";
        }
        setActionbarBackDisplay(title);

    }

    //store result
    @Override
    public void onBackPressed() {
        Intent resultData = new Intent();
        resultData.putExtra(IntentIndexes.DATA_INDEX, field.getText().toString());
        setResult(Activity.RESULT_OK, resultData);
        super.onBackPressed();
    }


}
