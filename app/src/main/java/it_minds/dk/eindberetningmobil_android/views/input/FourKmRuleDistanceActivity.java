package it_minds.dk.eindberetningmobil_android.views.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.NumberPicker;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by jonas on 09/06/16.
 */
public class FourKmRuleDistanceActivity extends ProvidedSimpleActivity {
    private NumberPicker commaPicker;
    private NumberPicker mainCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_picker_view);

        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);

        setActionbarBackDisplay(title);

        mainCounter = getViewById(R.id.numberPicker2);
        mainCounter.setMaxValue(2000);
        mainCounter.setValue(1);
        mainCounter.setMinValue(0);


        commaPicker = getViewById(R.id.numberPicker);
        commaPicker.setMinValue(0);
        commaPicker.setMaxValue(9);
        //start by parsing the string as a double.
        String value = getIntent().getStringExtra(IntentIndexes.DATA_INDEX);

        double toDisplay = Double.parseDouble(value)/1000.0;

        //now lets to some magic. we misuse the fact that int will not be rounded.
        mainCounter.setValue((int) toDisplay);
        commaPicker.setValue(getComma(toDisplay)); //and the rest is just math fun.
    }

    /**
     * Gets the first decimal of a double number.
     *
     * @param toDisplay the whole double
     * @return the first digit after "." (or in danish ",") in a double.
     */
    private int getComma(double toDisplay) {
        int d1 = (int) toDisplay;
        return (int) ((toDisplay - d1) * 10);
    }

    /**
     * save result.
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        //convert km (eg 2,1) to meters (2100). the math should be fairly simple
        String value = (((mainCounter.getValue() * 10) + commaPicker.getValue()) * 100) + "";
        i.putExtra(IntentIndexes.DATA_INDEX, value);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
