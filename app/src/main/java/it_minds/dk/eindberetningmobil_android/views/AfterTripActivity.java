package it_minds.dk.eindberetningmobil_android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnData;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;

/**
 * Created by kasper on 29-06-2015.
 */
public class AfterTripActivity extends SimpleActivity {

    DrivingReport report = new DrivingReport();

    private final static int TEXT_INPUT_CODE = 556;

    private OnData<String> afterEditCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        setContentView(R.layout.after_tracking_view);
        findViewById(R.id.after_tracking_view_send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterTripActivity.this, UploadingView.class);
                intent.putExtra(IntentIndexes.DATA_INDEX, report);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.after_tracking_view_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navgateBack();
            }
        });
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

        findViewById(R.id.after_tracking_view_extra_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setExtraDescription(data);
                    }
                }, getString(R.string.extra_description_title_edit), report.getExtraDescription());
            }
        });
        findViewById(R.id.after_tracking_view_purpose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setPurpose(data);
                    }
                }, getString(R.string.purpose_title_edit), report.getPurpose());
            }
        });
        findViewById(R.id.after_tracking_view_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setRate(data);
                    }
                }, getString(R.string.rate_title_edit), report.getRate());
            }
        });

        findViewById(R.id.after_tracking_view_org_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        report.setOrgLocation(data);
                    }
                }, getString(R.string.org_location_title_edit), report.getOrgLocation());
            }
        });

        findViewById(R.id.after_tracking_view_km_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEdit(new OnData<String>() {
                    @Override
                    public void onData(String data) {
                        double meters = report.getdistanceInMeters();
                        try {
                            meters = Double.parseDouble(data);
                            report.sethaveEditedDistance(true);
                        } catch (Exception e) {
                        }
                        report.setdistanceInMeters(meters);
                    }
                }, getString(R.string.distance_title_edit), report.getdistanceInMeters() + "");
            }
        });
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

    private void showEdit(OnData<String> callback, String title, String currentValue) {
        afterEditCallback = callback;
        Intent intent = new Intent(AfterTripActivity.this, TextInputView.class);
        intent.putExtra(IntentIndexes.DATA_INDEX, currentValue);
        intent.putExtra(IntentIndexes.TITLE_INDEX, title);
        startActivityForResult(intent, TEXT_INPUT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TEXT_INPUT_CODE && data != null) {
            if (afterEditCallback != null) {
                afterEditCallback.onData(data.getStringExtra(IntentIndexes.DATA_INDEX));
            } else {
                Log.e("temp", "bug");
            }
        }
    }
}
