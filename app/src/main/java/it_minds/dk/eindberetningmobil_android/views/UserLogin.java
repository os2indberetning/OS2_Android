package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.ProvidedSimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.UserInfo;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

public class UserLogin extends ProvidedSimpleActivity {


    private EditText usernameInput;
    private EditText passwordInput;
    private TextInputLayout usernameWrapper;
    private TextInputLayout passwordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Super method handles topBar ui and color
        super.onCreate(savedInstanceState);

        MainSettings settings = MainSettings.getInstance(this);
        if(settings.getRates() != null &&
                settings.getProfile() != null &&
                settings.getProfile().getAuthorization() != null){

            handleSuccessfulLogin();
            return;
        }
        setupUI();
    }

    private void setupUI(){
        setContentView(R.layout.activity_user_login);

        usernameWrapper = (TextInputLayout) findViewById(R.id.user_login_username_wrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.user_login_password_wrapper);

        usernameInput = (EditText) findViewById(R.id.user_login_username);
        passwordInput = (EditText) findViewById(R.id.user_login_password);
        Button loginButton = (Button) findViewById(R.id.user_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(handleSimpleInputValidation()){
                    showProgressDialog();
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
                                        try {
                                            JSONObject responseData = new JSONObject(new String(err.networkResponse.data, "UTF-8"));
                                            Log.d("DATA:", responseData.toString());
                                            showLoginErrorToast(responseData.getString("ErrorMessage"));
                                        } catch (JSONException e) {
                                            showLoginErrorToast(String.format(getString(R.string.generic_network_error_message), err.networkResponse.statusCode, 1));
                                        } catch (Exception e) {
                                            showLoginErrorToast(String.format(getString(R.string.generic_network_error_message), err.networkResponse.statusCode, 2));
                                        }
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

    public void showLoginErrorToast(String message){
        Toast.makeText(UserLogin.this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean handleSimpleInputValidation(){
        if(TextUtils.isEmpty(usernameInput.getText())) {
            usernameWrapper.setError("Brugernavn kan ikke være tom.");
            return false;
        }else {
            usernameWrapper.setErrorEnabled(false);
        }

        if(TextUtils.isEmpty(passwordInput.getText())) {
            passwordWrapper.setError("Password kan ikke være tom.");
            return false;
        }else{
            passwordWrapper.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * Handles a UserInfo result from server with status code 200.
     * This methods makes sure the data is valid and completes login if data was valid.
     * @param result The result from the server as a UserInfo object
     */
    private void handleLoginResult(UserInfo result){
        if(result != null && result.getprofile() != null && result.getrates() != null){
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

    @Override
    public void onBackPressed() {
        MainSettings.getInstance(this).setProvider(null);
        super.onBackPressed();
    }
}
