package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.location.GpsMonitor;
import it_minds.dk.eindberetningmobil_android.models.internal.PrefilledData;
import it_minds.dk.eindberetningmobil_android.service.MonitoringService;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 28-06-2015.
 * this view is the begining of a monitoring of a trip.
 */
public class StartActivity extends BaseReportActivity {

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
        if (report.getRate() != null || report.getOrgLocation() != null || report.getPurpose() != null || report.getExtraDescription() != null) {
            new ConfirmationDialog(this, getString(R.string.start_cancel_dialog_title), getString(R.string.entering_will_dismiss), getString(R.string.Ok), getString(R.string.No), null, new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    superBackPressed();
                }

                @Override
                public void onError(Exception error) {

                }
            }).showDialog();
        } else {
            superBackPressed();
        }
    }

    private void superBackPressed() {
        super.onBackPressed();
    }

    private final View.OnClickListener onStartClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validateCommonFields()) {
                Toast.makeText(StartActivity.this, R.string.start_activity_validation_error, Toast.LENGTH_LONG).show();
                return;
            }

            if (!GpsMonitor.isGpsEnabled(StartActivity.this)) {
                Toast.makeText(StartActivity.this, R.string.activate_gps, Toast.LENGTH_SHORT).show();
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
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.startactivity_menu, menu);
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
