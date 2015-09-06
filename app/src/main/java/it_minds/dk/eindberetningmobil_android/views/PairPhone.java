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
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
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
        if (settings.haveToken()) { //do we have a token. if so verify it.
            Log.e("temp", "have token");
            final ProgressDialog spinner = new ProgressDialog(this);
            spinner.setIndeterminate(true);
            spinner.setMessage(getString(R.string.please_wait));
            spinner.show();
            ServerFactory.getInstance(this).validateToken(settings.getToken(), new ResultCallback<UserInfo>() {
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
                            if (error instanceof VolleyError) {
                                VolleyError verr = (VolleyError) error;
                                if (verr.networkResponse != null && verr.networkResponse.statusCode == 401) {
                                    Toast.makeText(PairPhone.this, getString(R.string.error_happend), Toast.LENGTH_LONG).show();
                                    MainSettings.getInstance(PairPhone.this).clearProfile(); //make sure we dont try it again.
                                    MainSettings.getInstance(PairPhone.this).clearToken(); //make sure we dont try it again.
                                    setupUI();
                                    return;
                                }
                            }
                            Toast.makeText(PairPhone.this, R.string.no_verication_error, Toast.LENGTH_SHORT).show();
                            useInternalToken();
                        }
                    }
            );
        } else {//if not, then allow the user to pair the phone.
            Log.e("temp", "dont have token");
            setupUI();
        }
        setActionbarBackDisplay();
        hideSoftkeyboard(); //make sure he can actually read the text.

    }

    private void setupUI() {
        setContentView(R.layout.pair_phone_view);
        pairPhoneField = getViewById(R.id.pair_phone_view_pair_field);
        Button pair_btn = getViewById(R.id.pair_phone_view_pair_btn);
        //the on pair button clicked
        pair_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog spinner = new ProgressDialog(PairPhone.this);
                spinner.setIndeterminate(true);
                spinner.setMessage(getString(R.string.please_wait));
                spinner.show();
                final String code = pairPhoneField.getText().toString();
                ServerFactory.getInstance(PairPhone.this).pairPhone(code, new ResultCallback<UserInfo>() { //try pair the device
                    @Override
                    public void onSuccess(UserInfo result) {
                        spinner.dismiss();
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
                        spinner.dismiss();
                        //in here we have to handle the varrious cases. which defines the meaning of the error.
                        Logger.getLogger("pairphone").log(Level.SEVERE, "", error);
                        if (error instanceof VolleyError) {
                            VolleyError verr = (VolleyError) error;
                            if (verr.networkResponse.statusCode == 400) {
                                //already used
                                Toast.makeText(PairPhone.this, R.string.token_already_used, Toast.LENGTH_SHORT).show();

                            } else if (verr.networkResponse.statusCode == 401) {
                                //token not found
                                Toast.makeText(PairPhone.this, R.string.error_token_not_found, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PairPhone.this, getString(R.string.error_happend) + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    //since we get a list of tokens, we must find the one we entered, as that is the primary token (and matching guid) we are going to use.
    public boolean findTokenInUserInfo(UserInfo result, String code) {
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
