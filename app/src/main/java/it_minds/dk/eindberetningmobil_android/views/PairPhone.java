package it_minds.dk.eindberetningmobil_android.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Token;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 28-06-2015.
 */
public class PairPhone extends SimpleActivity {

    private EditText pairPhoneField;
    private MainSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = MainSettings.getInstance(this);
        if (settings.haveToken()) {
            Log.e("temp", "have token");
            final ProgressDialog spinner = new ProgressDialog(this);
            spinner.setIndeterminate(true);
            spinner.setMessage("vent venligst");
            spinner.show();
            ServerHandler.getInstance(this).validateToken(settings.getToken(), new ResultCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    spinner.dismiss();
                    if (result) {
                        useToken();
                    } else {
                        Toast.makeText(PairPhone.this, "ugyldigt token", Toast.LENGTH_SHORT).show();
                        setupUI();
                    }
                }

                @Override
                public void onError(Exception error) {
                    spinner.dismiss();
                    Toast.makeText(PairPhone.this, "Fejl skete, " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    setupUI();
                }
            });
        } else {
            Log.e("temp", "dont have token");
            setupUI();
        }

    }

    private void setupUI() {
        setContentView(R.layout.pair_phone_view);
        pairPhoneField = getViewById(R.id.pair_phone_view_pair_field);
        findViewById(R.id.pair_phone_view_pair_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pairPhoneField.getText().toString();
                ServerHandler.getInstance(PairPhone.this).pairPhone(code, new ResultCallback<Token>() {
                    @Override
                    public void onSuccess(Token result) {
                        settings.setToken(result);
                        Log.e("temp", "token saved");
                        useToken();
                    }

                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(PairPhone.this, "Der skete en fejl, " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void useToken() {
        startActivity(new Intent(PairPhone.this, StartActivity.class));
        finish();
    }
}
