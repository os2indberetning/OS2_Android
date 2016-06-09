/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.HashMap;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.constants.DistanceDisplayer;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnData;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Profile;
import it_minds.dk.eindberetningmobil_android.models.internal.PrefilledData;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;
import it_minds.dk.eindberetningmobil_android.views.dialogs.InaccuracyDialog;
import it_minds.dk.eindberetningmobil_android.views.input.FourKmRuleKmActivity;
import it_minds.dk.eindberetningmobil_android.views.input.KmActivity;

/**
 * the view after we have monitored a trip
 */
public class AfterTripActivity extends BaseReportActivity {
    private double fourKmRuleValue = 0.0f;
    protected View FourKmRuleKmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        report.setEndTime(new DateTime());
        setContentView(R.layout.after_tracking_view);
        TextView sendBtn = getViewById(R.id.after_tracking_view_send_btn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnSend();
            }
        });
        setColorForText(sendBtn);
        TextView cancelBtn = getViewById(R.id.after_tracking_view_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateBack();
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

        CheckBox usingFourKMRule = getViewById(R.id.after_tracking_view_using_fourkm_rule);
        usingFourKMRule.setChecked(report.getfourKMRule());
        usingFourKMRule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                report.setfourKMRule(isChecked);
                if (isChecked)
                {
                    FourKmRuleKmView.setVisibility(View.VISIBLE);
                }
                else
                {
                    FourKmRuleKmView.setVisibility(View.GONE);
                }
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
                final double prevVal = report.getDistanceInMeters();
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        double meters = handleKmClick(data, prevVal);
                        report.setDistanceInMeters(meters);
                    }
                }, getString(R.string.distance_title_edit), report.getDistanceInMeters() + "", KmActivity.class);
            }
        });

        TextView kmDescView = (TextView) findViewById(R.id.after_tracking_view_km_container_desc);
        kmDescView.setText(DistanceDisplayer.formatDistance(report.getDistanceInMeters()));


        FourKmRuleKmView = findViewById(R.id.after_tracking_view_fourkm_rule_km_container);
        FourKmRuleKmView.setVisibility(View.GONE);
        FourKmRuleKmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final double prevVal = fourKmRuleValue;
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        double meters = handleFourKmRuleKmClick(data, prevVal);
                        fourKmRuleValue = meters;
                    }
                }, getString(R.string.distance_title_edit), fourKmRuleValue + "", FourKmRuleKmActivity.class);
            }
        });

        TextView fourKmRuleKmDescView = (TextView)findViewById(R.id.after_tracking_view_fourkm_rule_km_container_desc);
        fourKmRuleKmDescView.setText(DistanceDisplayer.formatDistance(fourKmRuleValue));

        setDateLabel();
        setUserLabel();
        savePrefilledData();

        // Show inaccuracy warning if relevant
        boolean shouldShowWarning = getIntent().getBooleanExtra(IntentIndexes.POSSIBLE_INACCURACY_WARNING_INDEX, false);
        if (shouldShowWarning) {
            InaccuracyDialog id = new InaccuracyDialog(this);
            id.showDialog();
        }
    }

    private void savePrefilledData() {

        String purpose = report.getPurpose();
        String rateId = report.getRate();
        String orgId = report.getOrgLocation();

        PrefilledData data = new PrefilledData(purpose, rateId, orgId);
        MainSettings.getInstance(this).setPrefilledData(data);
    }

    private void handleOnSend() {
        if (!validateCommonFields()) {
            Toast.makeText(this, R.string.start_activity_validation_error, Toast.LENGTH_LONG).show();
        } else {
            showDialogBeforeSend();
        }
    }

    private void setDateLabel() {
        TextView dateLabel = getViewById(R.id.after_tracking_view_date_label);
        String dateString;

        if (report.getstartTime() != null) {
            dateString = report.getstartTime().toString("dd/MM-YY");
        } else {
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
        double meters = report.getDistanceInMeters();
        try {
            meters = Double.parseDouble(data);
            report.sethaveEditedDistance(meters != prevVal || report.getHaveEditedDistance());//if we have edited it, it stays that way.
            setTextToView(R.id.after_tracking_view_km_container_desc, DistanceDisplayer.formatDistance(meters));
        } catch (Exception e) {
            Log.e("temp", "is not a decimal number", e);
        }
        return meters;
    }

    private double handleFourKmRuleKmClick(String data, double prevVal) {
        double meters = fourKmRuleValue;
        try {
            meters = Double.parseDouble(data);
            setTextToView(R.id.after_tracking_view_fourkm_rule_km_container_desc, DistanceDisplayer.formatDistance(meters));
        } catch (Exception e) {
            Log.e("temp", "is not a decimal number", e);
        }
        return meters;
    }

    @Override
    public void onBackPressed() {
        navigateBack();
    }

    private void navigateBack() {
        //show a confirmation dialog.
        new ConfirmationDialog(this, getString(R.string.dialog_cancel_full_report), getString(R.string.cancel_full_report_message), getString(R.string.delete), getString(R.string.No), null, new ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                startActivity(new Intent(AfterTripActivity.this, StartActivity.class));
                finish();
            }

            @Override
            public void onError(Exception error) {

            }
        }).showDialog();
    }

    private void showDialogBeforeSend() {
        new ConfirmationDialog(this, getString(R.string.dialog_send_full_report), getString(R.string.dialog_send_full_report_message), getString(R.string.send), getString(R.string.No), null, new ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                Intent intent = new Intent(AfterTripActivity.this, UploadingView.class);
                intent.putExtra(IntentIndexes.DATA_INDEX, report);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Exception error) {

            }
        }).showDialog();
    }

}
