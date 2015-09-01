package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.MissingTripsAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 01-09-2015.
 */
public class MissingTripActivity extends BaseReportActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_trips_view);
        listView = getViewById(R.id.missing_trips_listview);
        refreshData();
    }

    private void refreshData() {
        //load from settings
        //MainSettings.getInstance(this).
        MissingTripsAdapter adapter = new MissingTripsAdapter(this);
        adapter.add(new SaveableReport("{}", "test mig", "1", 200, new DateTime()));
        adapter.add(new SaveableReport("{}", "test mig2", "12", 2000, new DateTime()));
        adapter.add(new SaveableReport("{}", "test mig3", "13", 2300, new DateTime()));
        adapter.add(new SaveableReport("{}", "test mig4", "14", 2400, new DateTime()));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SaveableReport report = (SaveableReport) parent.getItemAtPosition(position);
                ConfirmationDialog dialog = new ConfirmationDialog(MissingTripActivity.this, "Rapport", "Du har ikke sendt denne rapport, hvad vil du ?", "send", "slet", null, new ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        Toast.makeText(MissingTripActivity.this, "asd", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception error) {
                        MainSettings.getInstance(MissingTripActivity.this).removeSavedReport(report);
                    }
                });
                dialog.showDialog();
            }
        });
    }
}
