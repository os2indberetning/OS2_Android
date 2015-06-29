package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.Timer;
import java.util.TimerTask;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 29-06-2015.
 */
public class UploadingView extends SimpleActivity {

    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploading_view);
        statusText = getViewById(R.id.upload_view_status_text);
        String url =  MainSettings.getInstance(this).getProvider().getImgUrl();
        NetworkImageView img = getViewById(R.id.uploading_view_image);
        img.setImageUrl(url, ServerHandler.getInstance(this).getImageLoader());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(UploadingView.this, StartActivity.class));
                finish();
            }
        }, 5000);
    }

    public void updateStatusText(String text) {
        statusText.setText(text);
    }
}
