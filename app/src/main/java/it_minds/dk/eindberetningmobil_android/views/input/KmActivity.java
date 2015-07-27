package it_minds.dk.eindberetningmobil_android.views.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.NumberPicker;

import java.text.DecimalFormat;
import java.text.ParseException;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 27-07-2015.
 */
public class KmActivity extends ProvidedSimpleActivity {
    private NumberPicker commaPicker;
    private NumberPicker mainCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance_picker_view);
//        ListView lw = getViewById(R.id.rate_select_list_view);

        new ConfirmationDialog(this, getString(R.string.km_edit_notice_title),
                getString(R.string.km_edit_notice_body), getString(R.string.km_edit_notice_accept),
                getString(R.string.km_edit_notice_decline), null, new ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                //just continue.
            }

            @Override
            public void onError(Exception error) {
                finish(); //dont return a result.
            }
        }).showDialog();

        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);


        setActionbarBackDisplay(title);

        mainCounter = getViewById(R.id.numberPicker2);
        mainCounter.setMaxValue(2000);
        mainCounter.setValue(1);
        mainCounter.setMinValue(0);


        commaPicker = getViewById(R.id.numberPicker);
        commaPicker.setMinValue(0);
        commaPicker.setMaxValue(9);
        String value = getIntent().getStringExtra(IntentIndexes.DATA_INDEX);
        DecimalFormat df = new DecimalFormat();
        double toDisplay = 0;
        try {
            toDisplay = df.parse(value).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mainCounter.setValue((int) toDisplay);
        commaPicker.setValue(getComma(toDisplay));


//        lw.setAdapter(new RateAdapter(this, rates));
//        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent();
//                i.putExtra(IntentIndexes.DATA_INDEX, rates.get(position).getId()+"");
//                setResult(Activity.RESULT_OK, i);
//                finish();
//            }
//        });

    }

    private int getComma(double toDisplay) {
        int d1 = (int) toDisplay;
        return (int) ((toDisplay - d1) * 10);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        String value = mainCounter.getValue() + "." + commaPicker.getValue();
        i.putExtra(IntentIndexes.DATA_INDEX, value);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
