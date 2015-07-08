package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Timer;
import java.util.TimerTask;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 29-06-2015.
 * the view handling sending the report, and displaying the providers logo
 */
public class UploadingView extends ProvidedSimpleActivity {

    private DrivingReport report;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploading_view);
        report = getIntent().getParcelableExtra(IntentIndexes.DATA_INDEX);
        statusText = getViewById(R.id.upload_view_status_text);
        String url = MainSettings.getInstance(this).getProvider().getImgUrl();
        NetworkImageView img = getViewById(R.id.uploading_view_image);
        img.setImageUrl(url, ServerHandler.getInstance(this).getImageLoader());
        final Timer timer = new Timer();//TODO have some real stuff here instead of waiting.
        ServerHandler.getInstance(this).sendReport(report, new ResultCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                updateStatusText("success");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        startActivity(new Intent(UploadingView.this, StartActivity.class));
                        finish();
                    }
                }, 5000);
            }

            @Override
            public void onError(Exception error) {
                updateStatusText("fejl");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        startActivity(new Intent(UploadingView.this, StartActivity.class));
                        finish();
                    }
                }, 5000);
            }
        });
    }

    public void updateStatusText(String text) {
        statusText.setText(text);
    }
}
