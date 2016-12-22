/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.MissingTripsAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.SaveableDriveReport;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Activity for showing locally saved driveReports
 * Handles retry of sending the saved reports.
 */
public class MissingTripActivity extends ProvidedSimpleActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_trips_view);
        listView = getViewById(R.id.missing_trips_listview);
        setActionbarBackDisplay();
        setTitle(R.string.not_sent_reports_view_title);
        refreshData();
    }

    public void refreshData() {
        MissingTripsAdapter adapter = new MissingTripsAdapter(this);
        adapter.addAll(MainSettings.getInstance(this).getDrivingReports());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final SaveableReport report = (SaveableReport) parent.getItemAtPosition(position);
                ConfirmationDialog dialog = new ConfirmationDialog(MissingTripActivity.this,
                        getString(R.string.report_title), getString(R.string.missing_report_dialog_message),
                        getString(R.string.send),
                        getString(R.string.delete),
                        null, new ResultCallback<Boolean>() {
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
        SaveableDriveReport driveReport = new SaveableDriveReport(MainSettings.getInstance(this).getProfile().getAuthorization(), report);
        ServerFactory.getInstance(this).sendSavedReport(driveReport, new ResultCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject result) {
                MainSettings.getInstance(MissingTripActivity.this).removeSavedReport(report);
                Toast.makeText(MissingTripActivity.this, R.string.send_and_recived, Toast.LENGTH_SHORT).show();
                refreshData();
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(MissingTripActivity.this, R.string.error_sending_retry_later, Toast.LENGTH_LONG).show();
            }
        });
    }
}
