package it_minds.dk.eindberetningmobil_android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnData;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;

/**
 * Created by kasper on 28-06-2015.
 * this view is the begining of a monitoring of a trip.
 */
public class StartActivity extends ProvidedSimpleActivity {

    private final DrivingReport report = new DrivingReport();

    private final static int TEXT_INPUT_CODE = 556;

    private OnData<String> afterEditCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_tracking_layout);
        TextView dateLabel = getViewById(R.id.start_tracking_layout_date_label);
        dateLabel.setText(new DateTime().toString("dd/MM-yy"));
        findViewById(R.id.start_tracking_layout_purpose).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.start_tracking_layout_org_location).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.start_tracking_layout_rate).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.start_tracking_layout_extra_desc).setOnClickListener(new View.OnClickListener() {
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

        CheckBox cb =  getViewById(R.id.start_tracking_layout_start_at_home);
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

    private final View.OnClickListener onStartClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Perform any required validation here.
            Intent startIntent = new Intent(StartActivity.this, MonitoringActivity.class);
            startIntent.putExtra(IntentIndexes.DATA_INDEX, report);
            startActivity(startIntent);
            finish();
        }
    };

    private void showEdit(OnData<String> callback, String title, String currentValue) {
        afterEditCallback = callback;
        Intent intent = new Intent(StartActivity.this, TextInputView.class);
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
