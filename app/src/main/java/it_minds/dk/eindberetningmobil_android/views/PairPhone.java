package it_minds.dk.eindberetningmobil_android.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Tokens;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 28-06-2015.
 * in this view we connect the user with the backend.
 */
public class PairPhone extends ProvidedSimpleActivity {

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
            ServerHandler.getInstance(this).validateToken(settings.getToken(), new ResultCallback<UserInfo>() {
                @Override
                public void onSuccess(UserInfo result) {
                    spinner.dismiss();
                    useToken();
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
        Button pair_btn = getViewById(R.id.pair_phone_view_pair_btn);
        pair_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pairPhoneField.getText().toString();
                ServerHandler.getInstance(PairPhone.this).pairPhone(code, new ResultCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
//                        settings.setToken(result);
                        //first find the correct token in the Tokens list, and then store that one.
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
        setColorForText(pair_btn);
    }

    private void useToken() {
        startActivity(new Intent(PairPhone.this, StartActivity.class));
        finish();
    }
}
