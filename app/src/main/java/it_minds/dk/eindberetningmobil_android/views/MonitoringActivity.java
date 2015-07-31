package it_minds.dk.eindberetningmobil_android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.service.MonitoringReciver;
import it_minds.dk.eindberetningmobil_android.service.MonitoringService;
import it_minds.dk.eindberetningmobil_android.service.UiStatusModel;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationEndDrivingDialog;

/**
 * Created by kasper on 28-06-2015.
 * The main activity for an active drive / report.
 */
public class MonitoringActivity extends ProvidedSimpleActivity {
    /**
     * The current report
     */
    private DrivingReport localReport;
    /**
     * State used to show the "stop" dialog, since the events are unaware of eachother.
     */
    private boolean justCanceled = false;
    private ConfirmationDialog invalidGpsDialog;
    private boolean active;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitoring_view);

        TextView pauseResume = (TextView) getPauseButton();
        TextView stopBtn = getViewById(R.id.monitoring_view_stop_btn);
        setColorForText(pauseResume);
        setColorForText(stopBtn);
        getAccTextView().setText(R.string.waiting_for_gps);


        localReport = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        startService(localReport);

        MonitoringService.getListeningState(this);

        getPauseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonitoringService.pauseResumeListening(MonitoringActivity.this);
                MonitoringService.getListeningState(MonitoringActivity.this);
            }
        });
        getStopButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRide();
            }
        });

    }

    private void stopRide() {
        justCanceled = true;
        MonitoringService.pauseResumeListening(MonitoringActivity.this);
    }

    private void startService(DrivingReport report) {
        MonitoringService.startService(this, onUpdate, report);
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

    /**
     * In here we handle all interaction with the service. This is the primary callback. We have
     * verious types of callback depending on what extra there are in the intent.
     * The DATA_INDEX is the whole report
     * The STATUS_INDEX determins the current listening status.
     * The UI_INDEX contains the ui state (accuracy, current distance, and last fix).
     * The ERROR_INDEX indicates when an error occurs, particular when the user perform some undesired
     * action.
     */
    private MonitoringReciver.Receiver onUpdate = new MonitoringReciver.Receiver() {
        @Override
        public void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == Activity.RESULT_OK && resultData != null) {
                if (resultData.containsKey(IntentIndexes.DATA_INDEX)) {
                    localReport = resultData.getParcelable(IntentIndexes.DATA_INDEX);
                    handleStopButton();
                    //report.
                } else if (resultData.containsKey(IntentIndexes.STATUS_INDEX)) {
                    // status.
                    boolean isListening = resultData.getBoolean(IntentIndexes.STATUS_INDEX);
                    handlePauseResumeButton(isListening);
                } else if (resultData.containsKey(IntentIndexes.UI_INDEX)) {
                    UiStatusModel newState = resultData.getParcelable(IntentIndexes.UI_INDEX);
                    updateUi(newState);
                } else if (resultData.containsKey(IntentIndexes.ERROR_INDEX)) {
                    showInvalidLocation();
                } else if (resultData.containsKey(IntentIndexes.ERROR_GPS_INDEX)) {
                    showInvalidGps();
                } else if (resultData.containsKey(IntentIndexes.WORKING_GPS_INDEX)) {
                    removeInvalidGps();
                }
            }
        }
    };

    public MonitoringReciver.Receiver getMainReciver(){
        return onUpdate;
    }

    private void removeInvalidGps() {
        if (invalidGpsDialog != null) {
            //dismiss required here ?? i dont think so.
            invalidGpsDialog = null;
        }
    }

    private void showInvalidGps() {
        if (invalidGpsDialog == null) {
            invalidGpsDialog = new ConfirmationDialog(this, getString(R.string.error_no_gps_title),
                    getString(R.string.error_no_gps_message),
                    getString(R.string.error_gps_retry),
                    getString(R.string.error_gps_stop), null, new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    MonitoringService.pauseResumeListening(MonitoringActivity.this);
                }

                @Override
                public void onError(Exception error) {
                    finishDrive(false);
                }
            });
        }
        if (active) {
            invalidGpsDialog.showDialog();//can we do this, is the activity running?
        } else {
            //if not, then show a toast. they always gets displayed.
            Toast.makeText(MonitoringActivity.this, R.string.stopped_trip_gps, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        active = true;
    }


    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    /**
     * Helper method to show a dialog confirming we want to end the drive, and if so moves us forward in the process.
     */
    private void handleStopButton() {
        if (justCanceled) {
            justCanceled = false;
            ConfirmationEndDrivingDialog dialog = new ConfirmationEndDrivingDialog(this, new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean endedAtHome) {
                    finishDrive(endedAtHome);
                }

                @Override
                public void onError(Exception error) {
                    MonitoringService.pauseResumeListening(MonitoringActivity.this);
                }
            });
            dialog.showDialog();
        }
    }

    /**
     * this methods just ends with the current report.
     *
     * @param endedAtHome
     */
    private void finishDrive(Boolean endedAtHome) {
        MonitoringService.stopService(MonitoringActivity.this);
        Intent intent = new Intent(MonitoringActivity.this, AfterTripActivity.class);
        DrivingReport report = localReport;
        report.setendedAtHome(endedAtHome);
        report.setEndTime(new DateTime());
        intent.putExtra(IntentIndexes.DATA_INDEX, report);
        MonitoringActivity.this.startActivity(intent);
        MonitoringActivity.this.finish();
    }

    /**
     * Simple helper function, updating the ui, given the current state.
     *
     * @param newState
     */
    private void updateUi(UiStatusModel newState) {
        getCurrentDistanceTextView().setText(newState.getCurrentDistance());
        getLastFixTextView().setText(newState.getLastUpdated());
        getAccTextView().setText(newState.getAccuracy());
    }

    /**
     * Updates the pause / resume button depending on the supplied state.
     *
     * @param isListening
     */
    private void handlePauseResumeButton(boolean isListening) {
        if (isListening) {
            showResumed();
        } else {
            showPaused();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showInvalidLocation() {
        Log.e("temp", "LOCATION TO FAR AWAY");
        new ConfirmationDialog(this, getString(R.string.error_dialog_title), getString(R.string.error_distance_resume),
                getString(R.string.retry), getString(R.string.cancel_route), null, new ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                MonitoringService.pauseResumeListening(MonitoringActivity.this);
            }

            @Override
            public void onError(Exception error) {
                finishDrive(false);
            }
        }).showDialog();
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
