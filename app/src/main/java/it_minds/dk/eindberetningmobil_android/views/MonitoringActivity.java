package it_minds.dk.eindberetningmobil_android.views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.controllers.MonitoringController;

/**
 * Created by kasper on 28-06-2015.
 */
public class MonitoringActivity extends SimpleActivity {

    private MonitoringController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_view);
        controller = new MonitoringController(this);
        controller.startListing();
    }

    public TextView getAccTextView() {
        return getViewById(R.id.monitoring_view_acc_text);
    }

    public TextView getCurrentDistanceTextView() {
        return getViewById(R.id.monitoring_view_dist_text);
    }

    public TextView getLastFixTextView() {
        return getViewById(R.id.monitoring_view_acc_textmonitoring_view_last_fix_text);
    }

    public View getPauseButton() {
        return findViewById(R.id.monitoring_view_pause_resume_btn);
    }

    public View getStopButton() {
        return findViewById(R.id.monitoring_view_stop_btn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.stopListning();
    }
}
