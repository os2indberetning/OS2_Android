package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;

/**
 * Created by kasper on 29-06-2015.
 */
public class AfterTripActivity extends SimpleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_tracking_view);
        findViewById(R.id.after_tracking_view_send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AfterTripActivity.this, UploadingView.class));
                finish();
            }
        });
        findViewById(R.id.after_tracking_view_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navgateBack();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navgateBack();
    }

    private void navgateBack(){
        startActivity(new Intent(AfterTripActivity.this, StartActivity.class));
        finish();
    }
}
