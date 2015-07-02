package it_minds.dk.eindberetningmobil_android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;

/**
 * Created by kasper on 28-06-2015.
 * a view that allows the user to have a fullscreen text input.
 */
public class TextInputView extends ProvidedSimpleActivity {
    private EditText field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.simple_text_input_view);
        field = getViewById(R.id.simple_text_input_view_field);
        String providedText = getIntent().getStringExtra(IntentIndexes.DATA_INDEX);
        if (providedText != null) {
            field.setText(providedText);
        }
        String title =getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);
        if(title==null){
            title ="";
        }
        setActionbarBackDisplay(title);
    }

    @Override
    public void onBackPressed() {
        Intent resultData = new Intent();
        resultData.putExtra(IntentIndexes.DATA_INDEX, field.getText().toString());
        setResult(Activity.RESULT_OK, resultData);
        super.onBackPressed();
    }
}
