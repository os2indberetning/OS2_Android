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
import it_minds.dk.eindberetningmobil_android.adapters.EmploymentAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.models.Employments;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 27-07-2015.
 */
public class EmployementActivity extends ProvidedSimpleActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employment_select_list);
        ListView lw = getViewById(R.id.employment_select_list);

        String title = getIntent().getStringExtra(IntentIndexes.TITLE_INDEX);


        setActionbarBackDisplay(title);
        if (MainSettings.getInstance(this).getProfile() == null) {
            Toast.makeText(this, R.string.error_employments_nothing_to_display, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        final ArrayList<Employments> employments = MainSettings.getInstance(this).getProfile().getEmployments();
        if (employments == null) {
            Toast.makeText(this, R.string.error_employments_nothing_to_display, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        lw.setAdapter(new EmploymentAdapter(this, employments));
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra(IntentIndexes.DATA_INDEX, employments.get(position).getId() + "");
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

    }
}
