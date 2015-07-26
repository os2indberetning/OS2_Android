package it_minds.dk.eindberetningmobil_android.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    if (findTokenInUserInfo(result, MainSettings.getInstance(PairPhone.this).getToken().getTokenString())) {
                        Log.e("temp", "updated token from validate, and found current token string");
                    } else {
                        Log.e("temp", "updated token from validate, BUT DID NOT FIND CURRENT TOKEN STRING!??!");
                    }
                    useInternalToken();
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
        setActionbarBackDisplay();
        hideSoftkeyboard();

    }

    private void setupUI() {
        setContentView(R.layout.pair_phone_view);
        pairPhoneField = getViewById(R.id.pair_phone_view_pair_field);
        Button pair_btn = getViewById(R.id.pair_phone_view_pair_btn);
        pair_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = pairPhoneField.getText().toString();
                ServerHandler.getInstance(PairPhone.this).pairPhone(code, new ResultCallback<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo result) {
//                        settings.setToken(result);
                        //first find the correct token in the Tokens list, and then store that one.
                        Log.e("temp", "token saved");

                        if (findTokenInUserInfo(result, code)) {
                            useInternalToken();
                        } else {
                            Toast.makeText(PairPhone.this, R.string.generic_error_message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception error) {
                        Logger.getLogger("pairphone").log(Level.SEVERE, "", error);
                        if (error instanceof VolleyError) {
                            VolleyError verr = (VolleyError) error;
                            if (verr.networkResponse.statusCode == 400) {
                                //already used
                                Toast.makeText(PairPhone.this, "Tokenet er allerede brugt", Toast.LENGTH_SHORT).show();

                            } else if (verr.networkResponse.statusCode == 401) {
                                //token not found
                                Toast.makeText(PairPhone.this, R.string.error_token_not_found, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PairPhone.this, "Der skete en fejl, " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PairPhone.this, R.string.generic_error_message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        setColorForText(pair_btn);
    }

    private boolean findTokenInUserInfo(UserInfo result, String code) {
        if (result != null && result.getprofile() != null && result.getprofile().getTokens() != null) {
            ArrayList<Tokens> tokens = result.getprofile().getTokens();
            for (Tokens tok : tokens) {
                if (tok.getTokenString().equals(code)) {
                    MainSettings.getInstance(this).setToken(tok);
                    MainSettings.getInstance(this).setRates(result.getrates());
                    MainSettings.getInstance(this).setProfile(result.getprofile());
                    return true;
                }
            }
        } else {
            //invalid token response ??
            Toast.makeText(PairPhone.this, R.string.unknown_token_error, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        settings.setProvider(null);
        startActivity(new Intent(this, ChooseProvider.class));
        super.onBackPressed();
    }

    private void useInternalToken() {
        startActivity(new Intent(PairPhone.this, StartActivity.class));
        finish();
    }
}
