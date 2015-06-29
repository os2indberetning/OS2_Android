package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;

/**
 * Created by kasper on 28-06-2015.
 */
public class StartActivity extends SimpleActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_tracking_layout);
        TextView dateLabel = getViewById(R.id.start_tracking_layout_date_label);
        dateLabel.setText(new DateTime().toString("dd/MM-yy"));
        findViewById(R.id.start_tracking_layout_purpose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, TextInputView.class));
            }
        });
        findViewById(R.id.start_tracking_layout_org_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, TextInputView.class));
            }
        });
        findViewById(R.id.start_tracking_layout_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, TextInputView.class));
            }
        });
        findViewById(R.id.start_tracking_layout_extra_desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, TextInputView.class));

            }
        });
        findViewById(R.id.start_tracking_layout_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Perform any required validation here.
                Intent startIntent = new Intent(StartActivity.this, MonitoringActivity.class);
                //TODO startIntent.putExtra() the data to pass on.
                startActivity(startIntent);
                finish();
            }
        });


    }


}
