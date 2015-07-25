package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 28-06-2015.
 * this view is the begining of a monitoring of a trip.
 */
public class StartActivity extends BaseReportActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            //TODO Perform any required validation here. ?? is there any.
            Intent startIntent = new Intent(StartActivity.this, MonitoringActivity.class);
            startIntent.putExtra(IntentIndexes.DATA_INDEX, report);
            startActivity(startIntent);
            finish();
        }
    };


}
