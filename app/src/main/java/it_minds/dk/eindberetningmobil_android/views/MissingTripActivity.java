package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.MissingTripsAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 01-09-2015.
 */
public class MissingTripActivity extends ProvidedSimpleActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_trips_view);
        listView = getViewById(R.id.missing_trips_listview);
        setActionbarBackDisplay();
        MainSettings.getInstance(this).addReport(new SaveableReport("","formål her", "1",2000.d, new DateTime()));
        refreshData();
    }

    private void refreshData() {
        //load from settings
        //MainSettings.getInstance(this).
        MissingTripsAdapter adapter = new MissingTripsAdapter(this);
        adapter.addAll(MainSettings.getInstance(this).getDrivingReports());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SaveableReport report = (SaveableReport) parent.getItemAtPosition(position);
                ConfirmationDialog dialog = new ConfirmationDialog(MissingTripActivity.this, "Rapport", "Du har ikke sendt denne rapport, hvad vil du ?", "send", "slet", null, new ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        trySend(report);
                    }

                    @Override
                    public void onError(Exception error) {
                        MainSettings.getInstance(MissingTripActivity.this).removeSavedReport(report);
                        refreshData();
                    }
                });
                dialog.setCanCancel(true);
                dialog.showDialog();
            }
        });
    }

    private void trySend(final SaveableReport report) {
        SaveableDriveReport driveReport = new SaveableDriveReport(MainSettings.getInstance(this).getToken(), report);
        ServerHandler.getInstance(this).sendReport(driveReport, new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                MainSettings.getInstance(MissingTripActivity.this).removeSavedReport(report);
                Toast.makeText(MissingTripActivity.this, "sendt..", Toast.LENGTH_SHORT).show();
                refreshData();

            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(MissingTripActivity.this, "kunne ikke sende den, prøv igen senere.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
