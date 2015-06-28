package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.widget.TextView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;

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
    }

    public void updateStatusText(String text) {
        statusText.setText(text);
    }
}
