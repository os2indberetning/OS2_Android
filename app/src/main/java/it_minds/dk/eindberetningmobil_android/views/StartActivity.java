/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.constants.GPSAccesCode;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.location.GpsMonitor;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.models.internal.PrefilledData;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.service.MonitoringService;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ErrorDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import static it_minds.dk.eindberetningmobil_android.constants.GPSAccesCode.GPS_DISABLED_GLOBAL_SETTINGS;
import static it_minds.dk.eindberetningmobil_android.constants.GPSAccesCode.GPS_DISABLED_MARSHMALLOW;
import static it_minds.dk.eindberetningmobil_android.constants.GPSAccesCode.GPS_ENABLED;

/**
 * Activity for setting up info for the driveReport
 */
public class StartActivity extends BaseReportActivity {

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1002;
    int badgeCount = 0;
    boolean didFailSync = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if there are any ongoing trips, go to the monitoring class then.
        if (!MainSettings.getInstance(this).getServiceClosed() && MonitoringService.isServiceActive()) {
            startActivity(new Intent(this, MonitoringActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.start_tracking_layout);

        setTitle(R.string.new_drive_view_title);

        TextView dateLabel = getViewById(R.id.start_tracking_layout_date_label);
        dateLabel.setText(getString(R.string.date_start) + new DateTime().toString("dd/MM-yy"));

        handlePurpose(R.id.start_tracking_layout_purpose, R.id.start_tracking_layout_purpose_description);
        handleOrgLocation(R.id.start_tracking_layout_org_location, R.id.start_tracking_layout_org_location_description);

        handleRate(R.id.start_tracking_layout_rate, R.id.start_tracking_layout_rate_desc);
        handleExtraDesc(R.id.start_tracking_layout_extra_desc, R.id.start_tracking_layout_extra_desc_desc);

        CheckBox cb = getViewById(R.id.start_tracking_layout_start_at_home);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                report.setstartedAtHome(isChecked);
            }
        });

        TextView startBtn = getViewById(R.id.start_tracking_layout_start_btn);
        startBtn.setOnClickListener(onStartClicked);
        setColorForText(startBtn);

        loadPreFilledData();

    }

    @Override
    public void onResume() {
        super.onResume();
        //Update count when coming from the see_not_send view
        badgeCount = MainSettings.getInstance(this).getDrivingReports().size();
        //Invalidates and forces the bar to update.
        invalidateOptionsMenu();

        //Make sure userData is up to date
        syncProfile();
    }

    private void syncProfile(){
        showProgressDialog();

        MainSettings settings = MainSettings.getInstance(this);

        if(settings.getProfile() != null && settings.getProfile().getAuthorization() != null){
            ServerFactory.getInstance(this).syncUserInfo(MainSettings.getInstance(this).getProfile().getAuthorization().saveGuIdToJson(),
                    new ResultCallback<UserInfo>() {
                        @Override
                        public void onSuccess(UserInfo result) {
                            didFailSync = false;

                            MainSettings.getInstance(getApplicationContext()).setRates(result.getrates());
                            MainSettings.getInstance(getApplicationContext()).setProfile(result.getprofile());

                            dismissProgressDialog();
                        }

                        @Override
                        public void onError(Exception error) {
                            if(error instanceof VolleyError){
                                VolleyError vError = (VolleyError) error;
                                if(vError instanceof NoConnectionError){
                                    //No internet
                                    handleSyncFail(getString(R.string.user_sync_error_no_internet), getString(R.string.network_error_no_internet_description));
                                }else if(vError instanceof AuthFailureError && vError.networkResponse.statusCode == 401){
                                    //Users info was changed on the server (Possibly a password reset)
                                    handleSyncFail(getString(R.string.user_sync_error_unauthorized),getString(R.string.user_sync_error_unauthorized_description));
                                }else{
                                    handleSyncFail(getString(R.string.user_sync_error_unauthorized), getString(R.string.user_sync_error_unknown));
                                }
                            }

                            dismissProgressDialog();
                        }
                    });
        }
    }

    private void handleSyncFail(String title, String message){
        new ConfirmationDialog(this,
                title,
                message,
                "Log ind igen",
                "Forsøg igen", null, new ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                didFailSync = true;
                onBackPressed();
            }

            @Override
            public void onError(Exception error) {
                syncProfile();
            }
        }).showDialog();
    }

    private void loadPreFilledData() {
        PrefilledData prefilledData = MainSettings.getInstance(this).getPrefilledData();
        if (prefilledData == null) {
            return;
        }
        report.setOrgLocation(prefilledData.getOrgId());
        report.setRate(prefilledData.getRateId());
//        report.setPurpose(prefilledData.getPurposeText());
        setOrgText(prefilledData.getOrgId(), R.id.start_tracking_layout_org_location_description);
        setRateText(prefilledData.getRateId(), R.id.start_tracking_layout_rate_desc);
//        setTextToView(R.id.start_tracking_layout_purpose_description, prefilledData.getPurposeText());
    }

    @Override
    public void onBackPressed() {
            new ConfirmationDialog(this,
                    getString(R.string.confirmation_logout_drivereport_title),
                    getString(R.string.confirmation_logout_drivereport_description),
                    getString(R.string.confirmation_logout_drivereport_accept),
                    getString(R.string.confirmation_logout_drivereport_cancel), null, new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    superBackPressed();
                }

                @Override
                public void onError(Exception error) {
                    if(didFailSync){
                        syncProfile();
                    }
                }
            }).showDialog();
    }

    private void superBackPressed() {
        clearLocalUserData();
        super.onBackPressed();
    }

    private void clearLocalUserData(){
        ServerFactory.getInstance(this).setBaseUrl(null);

        Log.d("DEBUG", "Cleared local userdata");

        //Reset user specific data
        MainSettings settings = MainSettings.getInstance(this);
        settings.logoutClear();
    }

    private final View.OnClickListener onStartClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleStartDrive();
        }
    };

    private void handleStartDrive(){
        if (!validateCommonFields()) {
            Toast.makeText(StartActivity.this, R.string.start_activity_validation_error, Toast.LENGTH_LONG).show();
            return;
        }

        // check for notification permission
        int notificationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
        int fineLocPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int backgroundLocPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if (notificationPermissionCheck == PackageManager.PERMISSION_DENIED) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("OS2indberetning Kørsel kræver adgang notifikationer for at påminde om igangværende kørsel")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        String[] permissions = {Manifest.permission.POST_NOTIFICATIONS};
                        requestPermissions(permissions, NOTIFICATION_PERMISSION_REQUEST_CODE);
                        dialog.dismiss();
                    })
                    .show();
            return;
        }

        if (fineLocPermissionCheck ==  PackageManager.PERMISSION_DENIED ||coarseLocPermissionCheck == PackageManager.PERMISSION_DENIED) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("OS2indberetning Kørsel indsamler lokationsdata i baggrunden for at registrere den kørte rute korrekt")
                    .setPositiveButton("Slå til", (dialog, which) -> {
                        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                        requestPermissions(permissions, LOCATION_PERMISSION_REQUEST_CODE);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Afvis", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
            return;
        }

        if (backgroundLocPermissionCheck == PackageManager.PERMISSION_DENIED) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("OS2indberetning Kørsel indsamler lokationsdata i baggrunden for at registrere den kørte rute korrekt")
                    .setPositiveButton("Slå til", (dialog, which) -> {
                        showBackgroundLocationSettingsDialog();
                    })
                    .setNegativeButton("Afvis", (dialog, which) -> {
                        dialog.cancel();
                    })
                    .show();
            return;
        }

        //Check if GooglePlayServices is available and up to date

        //https://developers.google.com/android/reference/com/google/android/gms/common/GoogleApiAvailability
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        //https://developers.google.com/android/reference/com/google/android/gms/common/ConnectionResult
        if(result != ConnectionResult.SUCCESS){
            GooglePlayServicesUtil.getErrorDialog(result, StartActivity.this, 1).show();
            return;
        }

        report.setstartTime(new DateTime());
        Intent startIntent = new Intent(StartActivity.this, MonitoringActivity.class);
        startIntent.putExtra(IntentIndexes.DATA_INDEX, report);
        startActivity(startIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case NOTIFICATION_PERMISSION_REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.POST_NOTIFICATIONS)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            handleStartDrive();
                            return;
                        }
                    }
                }
                break;
            case LOCATION_PERMISSION_REQUEST_CODE:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            showBackgroundLocationSettingsDialog();
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void showBackgroundLocationSettingsDialog() {
        AlertDialog.Builder locationDialog = new AlertDialog.Builder(StartActivity.this);
        LayoutInflater factory = LayoutInflater.from(StartActivity.this);
        final View view = factory.inflate(R.layout.image_dialog, null);

        TextView title = (TextView) view.findViewById(R.id.location_settings_dialog_title_textview);
        title.setText("Opdatér indstillinger for lokation");

        TextView message = (TextView) view.findViewById(R.id.location_settings_dialog_message_textview);
        message.setText("Giv OS2indberetning adgang til lokation hele tiden for at registrere den kørte rute korrekt");

        locationDialog.setView(view);
        locationDialog.setPositiveButton("Indstillinger", (dialog, which) -> {
            //Now that we have the location permission we can ask for background location
            Uri uri = new Uri.Builder().scheme("package").opaquePart(getPackageName()).build();
            startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri));
            dialog.dismiss();
        });
        locationDialog.setNegativeButton("Afvis", (dialog, which) -> {
            dialog.cancel();
        });

        locationDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.startactivity_menu, menu);

        final MenuItem badgeItem = menu.findItem(R.id.missing_trip_menu_counter);
        final RelativeLayout badgeLayout = (RelativeLayout) badgeItem.getActionView();

        //Set badge
        if (badgeCount>0) {
            badgeItem.setVisible(true);

            TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
            tv.setText(badgeCount + "");
        } else {
            badgeItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.see_not_send) {
            //show activity
            startActivity(new Intent(this, MissingTripActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
