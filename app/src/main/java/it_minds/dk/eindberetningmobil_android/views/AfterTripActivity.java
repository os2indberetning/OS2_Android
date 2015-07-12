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
import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnData;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 29-06-2015.
 * the view after we have monitored a trip
 */
public class AfterTripActivity extends BaseReportActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        setContentView(R.layout.after_tracking_view);
        TextView sendBtn = getViewById(R.id.after_tracking_view_send_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterTripActivity.this, UploadingView.class);
                intent.putExtra(IntentIndexes.DATA_INDEX, report);
                startActivity(intent);
                finish();
            }
        });
        setColorForText(sendBtn);
        TextView cancelBtn = getViewById(R.id.after_tracking_view_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navgateBack();
            }
        });
        setReverseColorsForText(cancelBtn);

        CheckBox endedAtHome = getViewById(R.id.after_tracking_view_ended_at_home);
        endedAtHome.setChecked(report.getendedAtHome());
        endedAtHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                report.setendedAtHome(isChecked);
            }
        });

        CheckBox startAtHome = getViewById(R.id.after_tracking_view_start_at_home);
        startAtHome.setChecked(report.getstartedAtHome());
        startAtHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                report.setstartedAtHome(isChecked);
            }
        });


        handleExtraDesc(R.id.after_tracking_view_extra_desc, R.id.after_tracking_view_extra_desc_desc);
        handlePurpose(R.id.after_tracking_view_purpose, R.id.after_tracking_view_purpose_desc);
        handleRate(R.id.after_tracking_view_rate, R.id.after_tracking_view_rate_desc);
        handleOrgLocation(R.id.after_tracking_view_org_location, R.id.after_tracking_view_org_location_desc);
        View kmView = findViewById(R.id.after_tracking_view_km_container);
        kmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final double prevVal = report.getdistanceInMeters();
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        double meters = handleKmClick(data, prevVal);
                        report.setdistanceInMeters(meters);
                    }
                }, getString(R.string.distance_title_edit), report.getdistanceInMeters() + "");
            }
        });


        TextView kmDescView = (TextView) findViewById(R.id.after_tracking_view_km_container_desc);

        kmDescView.setText(DistanceDisplayer.formatDistance(report.getdistanceInMeters()));

        setDateLabel();
        setUserLabel();

    }

    private void setDateLabel() {
        TextView dateLabel = getViewById(R.id.after_tracking_view_date_label);
        String dateString ;

        if (report.getstartTime() != null) {
            dateString = report.getstartTime().toString("dd/MM-YY");
        }else{
            dateString = new DateTime().toString("dd/MM-YY");
        }
        dateLabel.setText(dateString);
    }

    private void setUserLabel() {
        TextView userLabel = getViewById(R.id.after_tracking_view_user_label);
        Profile profile = MainSettings.getInstance(this).getProfile();
        if (profile != null && profile.getFirstname() != null && profile.getLastname() != null) {
            userLabel.setText(profile.getFirstname() + " " + profile.getLastname());
        } else {
            userLabel.setText("");
        }
    }

    private double handleKmClick(String data, double prevVal) {
        double meters = report.getdistanceInMeters();
        try {
            meters = Double.parseDouble(data);
            report.sethaveEditedDistance(meters != prevVal);
            setTextToView(R.id.after_tracking_view_km_container_desc, meters + "");
        } catch (Exception e) {
        }
        return meters;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navgateBack();
    }

    private void navgateBack() {
        //show a confirmation dialog.
        startActivity(new Intent(AfterTripActivity.this, StartActivity.class));
        finish();
    }


}
