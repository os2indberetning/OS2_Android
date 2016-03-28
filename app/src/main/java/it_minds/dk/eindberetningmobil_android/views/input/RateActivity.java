package it_minds.dk.eindberetningmobil_android.views.input;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.RateAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.Rates;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Handles user selection of rates
 */
public class RateActivity extends ProvidedSimpleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_select_list);
        ListView lw = getViewById(R.id.rate_select_list_view);

        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);


        setActionbarBackDisplay(title);
        final ArrayList<Rates> rates = MainSettings.getInstance(this).getRates();
        if (rates == null) {
            Toast.makeText(this, "Der er ingen takster", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        lw.setAdapter(new RateAdapter(this, rates));
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra(IntentIndexes.DATA_INDEX, rates.get(position).getId()+"");
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

    }
}
