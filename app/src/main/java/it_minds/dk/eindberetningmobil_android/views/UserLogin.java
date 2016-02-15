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

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public class UserLogin extends ProvidedSimpleActivity {

    ProgressDialog spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Super method handles topBar ui and color
        super.onCreate(savedInstanceState);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setupUI();
    }

    private void setupUI(){
        setContentView(R.layout.activity_user_login);

        //Initialize spinner
        spinner = new ProgressDialog(this);

        final EditText usernameInput = (EditText) findViewById(R.id.user_login_username);
        final EditText passwordInput = (EditText) findViewById(R.id.user_login_password);
        Button loginButton = (Button) findViewById(R.id.user_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();

                //TODO: Handle userInput validation
                if(true){
                    //Try login with credentials
                    ServerFactory.getInstance(UserLogin.this).loginWithCredentials(
                            usernameInput.getText().toString(),
                            passwordInput.getText().toString(),
                            new ResultCallback<UserInfo>() {
                                @Override
                                public void onSuccess(UserInfo result) {
                                    dismissProgressDialog();
                                    handleLoginResult(result);
                                }

                                @Override
                                public void onError(Exception error) {
                                    dismissProgressDialog();

                                    if(error instanceof VolleyError){
                                        VolleyError err = (VolleyError) error;

                                        //TODO: Handle newtork response errors
                                        switch (err.networkResponse.statusCode) {
                                            case 404:

                                                break;
                                            case 403:

                                                break;
                                        }

                                        String statusCode = String.valueOf(err.networkResponse.statusCode);
                                        Log.d("DEBUG LOGIN ERROR", "Error info: " + statusCode + ": " + err.getMessage());
                                    }else{
                                        Log.d("DEBUG LOGIN ERROR", "Error: " + error.getLocalizedMessage());
                                        Toast.makeText(UserLogin.this, R.string.generic_error_message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                    );
                }

            }
        });

        //Setup button colors
        setColorForText(loginButton);
        //Enable backbutton in topbar
        setActionbarBackDisplay();
        //Hide initial keyboard
        hideSoftkeyboard(); //make sure he can actually read the text.
    }

    /**
     * Handles a UserInfo result from server with status code 200.
     * This methods makes sure the data is valid and completes login if data was valid.
     * @param result The result from the server as a UserInfo object
     */
    private void handleLoginResult(UserInfo result){
        if(result != null && result.getprofile() != null && result.getrates() != null){
            UserInfo temp = result;
            MainSettings.getInstance(this).setRates(result.getrates());
            MainSettings.getInstance(this).setProfile(result.getprofile());

            //Login was a success
            handleSuccessfulLogin();

        }else if (result != null){
            //TODO: Handle errors!!
            Log.d("DEBUG LOGIN", "Success - but something was wrong with the result (Either profile or rates)");
        } else {
            Log.d("DEBUG LOGIN", "Success - but result was null?");
        }
    }

    /**
     * Call after successful saving of user info
     */
    private void handleSuccessfulLogin(){
        startActivity(new Intent(UserLogin.this, StartActivity.class));
        finish();
    }

    private void showProgressDialog(){
        if(spinner !=null){
            spinner.setIndeterminate(true);
            spinner.setMessage(getString(R.string.please_wait));
            spinner.show();
        }
    }

    private void dismissProgressDialog(){
        if(spinner != null) {
            spinner.dismiss();
        }
    }
}
