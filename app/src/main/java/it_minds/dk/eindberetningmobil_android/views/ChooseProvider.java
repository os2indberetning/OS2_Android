/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import it_minds.dk.eindberetningmobil_android.BuildConfig;
import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.ProviderAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;
import it_minds.dk.eindberetningmobil_android.views.dialogs.ErrorDialog;

/**
 * Activity for choosing the wanted OS2 provider
 */
public class ChooseProvider extends SimpleActivity {

    private MainSettings settings;
    private ErrorDialog errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fetch providers and setup list
        setContentView(R.layout.choose_provider_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Only show menu if app is in debug mode
        if(BuildConfig.DEBUG){
            /*
                Test backend not accessible yet!
             */
//            MenuInflater inflater = getMenuInflater();
//            inflater.inflate(R.menu.provider_menu_debug, menu);
//            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.provider_menu_test_api){
            //Set the provider to Test Backend
            useProvider(new Provider(
                    "Test Backend",
                    "http://10.255.1.45:3308/api", //Not correct port number
                    "https://os2indberetning.dk/logo.png",
                    "#FFFFFF",
                    "#FFC107",
                    "#4CAF50"));
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshProviderList(){
        final ListView lw = getViewById(R.id.choose_provider_view_list);
        ServerFactory.getInstance(this).getProviders(new ResultCallback<List<Provider>>() {
            @Override
            public void onSuccess(final List<Provider> result) {
                lw.setAdapter(new ProviderAdapter(ChooseProvider.this, result));
                lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //select provider and continue.
                        lw.setOnItemClickListener(null);
                        useProvider(result.get(position));
                    }
                });
            }

            @Override
            public void onError(Exception error) {
                if (error instanceof VolleyError) {
                    VolleyError vError = (VolleyError) error;
                    if (vError.networkResponse != null) {
                        try {
                            JSONObject responseData = new JSONObject(new String(vError.networkResponse.data, "UTF-8"));
                            Log.d("DATA:", responseData.toString());
                        } catch (JSONException e) {
                            Toast.makeText(ChooseProvider.this, "Ukendt fejl ved download af udbydere\n(" + vError.networkResponse.statusCode + ") (1)", Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            Toast.makeText(ChooseProvider.this, "Ukendt fejl ved download af udbydere\n(" + vError.networkResponse.statusCode + ") (2)", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //We have not internet
                    }
                } else {
                    Toast.makeText(ChooseProvider.this, "Ukendt fejl ved download af udbydere.\n(3)", Toast.LENGTH_SHORT).show();
                    Log.d("ERROR", error.getMessage());
                }
                showProviderFetchError();
            }
        });
    }

    private void showProviderFetchError(){
        if(errorDialog == null){
            errorDialog = new ErrorDialog(this, "Kunne ikke hente/opdatere listen over udbydere. Tjek din internet forbindelse og forsøg igen.", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    errorDialog.dismissDialog();
                    refreshProviderList();
                }
            });
        }

        errorDialog.setIsCancelable(false);
        errorDialog.showDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check if we already have chosen af provider
        settings = MainSettings.getInstance(this);
        if (settings.haveProvider()) {
            useProvider(settings.getProvider());
            return;
        }

        refreshProviderList();
    }

    private synchronized void useProvider(Provider provider) {
        Log.e("temp", "chose provider" + provider.getName());
        settings.setProvider(provider);
        ServerFactory.getInstance(this).setBaseUrl(provider.getAPIUrl());

        startActivity(new Intent(this, UserLogin.class));
    }
}

