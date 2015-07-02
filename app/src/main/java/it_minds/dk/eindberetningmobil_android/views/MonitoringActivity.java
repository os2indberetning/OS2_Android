package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.controllers.MonitoringController;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;

/**
 * Created by kasper on 28-06-2015.
 */
public class MonitoringActivity extends ProvidedSimpleActivity {

    private MonitoringController controller;

    private DrivingReport report;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_view);
        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        controller = new MonitoringController(this);
        controller.startListing();

        TextView pauseResume = getViewById(R.id.monitoring_view_pause_resume_btn);
        TextView stopBtn = getViewById(R.id.monitoring_view_stop_btn);
        setColorForText(pauseResume);
        setColorForText(stopBtn);
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
        controller.stopListening();
    }

    public DrivingReport getReport() {
        return report;
    }

    public void showInvalidLocation() {
        Toast.makeText(this, "Hey du er ikke hvor du stoppede, dette er ikke OK, ...", Toast.LENGTH_LONG).show();
        Log.e("temp", "LOCATION TO FAR AWAY");
    }

    public void showResumed() {
        TextView tv = getViewById(R.id.monitoring_view_pause_resume_btn);
        tv.setText(R.string.pause_trip);
    }

    public void showPaused() {
        TextView tv = getViewById(R.id.monitoring_view_pause_resume_btn);
        tv.setText(R.string.resume_trip);
    }
}
