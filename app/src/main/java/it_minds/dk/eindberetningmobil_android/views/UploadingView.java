package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Timer;
import java.util.TimerTask;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DriveReport;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.models.internal.SaveableReport;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ConfirmationDialog;

/**
 * Created by kasper on 29-06-2015.
 * the view handling sending the report, and displaying the providers logo
 * and storing the current report to handle errors.
 */
public class UploadingView extends ProvidedSimpleActivity {

    private final static int WAIT_TIME_MS_SUCCESS_DISSAPEAR = 5000;

    private TextView statusText;
    private SaveableReport saveableReport;

    private DrivingReport report;
    private boolean haveSend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploading_view);

        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);

        checkForEmptyComment(report);

        statusText = getViewById(R.id.upload_view_status_text);
        if (MainSettings.getInstance(this).getProvider() != null) { //just to be sure we have any data.
            String url = MainSettings.getInstance(this).getProvider().getImgUrl();
            NetworkImageView img = getViewById(R.id.uploading_view_image);
            img.setImageUrl(url, ServerFactory.getInstance(this).getImageLoader()); //load logo.
        }


    }

    private void checkForEmptyComment(DrivingReport report) {
        if(report.getExtraDescription() == null){
            report.setExtraDescription("Ingen kommentar indtastet");
        }else{
            Log.d("DEBUG", "DEBUG");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!haveSend) {
            if (MainSettings.getInstance(this).getProfile() == null || report == null || MainSettings.getInstance(this).getToken() == null) {
                updateStatusText(getString(R.string.invalid_user));
                return;
            }
            haveSend = true;
            int profileId = MainSettings.getInstance(this).getProfile().getId();
            saveableReport = new SaveableReport(report, profileId);
            MainSettings.getInstance(this).addReport(saveableReport);
            DriveReport toSend = new DriveReport(MainSettings.getInstance(this).getToken(), report, profileId);
            final String json = toSend.saveAsJson().toString();
            Log.e("temp", json);
            TrySendReport(toSend);
        }

    }

    private void TrySendReport(final DriveReport toSend) {
        final Timer timer = new Timer();

        ServerFactory.getInstance(this).sendReport(toSend, new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                updateStatusText(getString(R.string.success));
                MainSettings.getInstance(UploadingView.this).removeSavedReport(saveableReport);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(UploadingView.this, StartActivity.class));
                                finish();
                            }
                        });

                    }
                }, WAIT_TIME_MS_SUCCESS_DISSAPEAR);
            }

            @Override
            public void onError(final Exception error) {
                updateStatusText(getString(R.string.error));
                Log.e("temp", "error", error);
                new ConfirmationDialog(UploadingView.this, getString(R.string.error_dialog_title), getString(R.string.send_report_error),
                        getString(R.string.send_report_error_retry), getString(R.string.send_report_error_cancel), null, new ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        TrySendReport(toSend);
                    }

                    @Override
                    public void onError(Exception error) {
                        startActivity(new Intent(UploadingView.this, StartActivity.class));
                        finish();
                    }
                }).showDialog();
            }
        });
    }

    public void updateStatusText(String text) {
        statusText.setText(text);
    }
}
