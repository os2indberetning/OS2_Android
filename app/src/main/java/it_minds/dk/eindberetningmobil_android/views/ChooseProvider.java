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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.adapters.ProviderAdapter;
import it_minds.dk.eindberetningmobil_android.baseClasses.SimpleActivity;
import it_minds.dk.eindberetningmobil_android.interfaces.ResultCallback;
import it_minds.dk.eindberetningmobil_android.models.Provider;
import it_minds.dk.eindberetningmobil_android.server.ServerFactory;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * in this view we choose a provider to use.
 */
public class ChooseProvider extends SimpleActivity {
    private MainSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Fetch providers and setup list
        setContentView(R.layout.choose_provider_view);
        //set content to the list.
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
                Toast.makeText(ChooseProvider.this, R.string.generic_error_message, Toast.LENGTH_SHORT).show();
            }
        });

        //Check if we already have chosen af provider
        settings = MainSettings.getInstance(this);
        if (settings.haveProvider()) {
            useProvider(settings.getProvider());
        }
    }

    private synchronized void useProvider(Provider provider) {
        Log.e("temp", "chose provider" + provider.getName());
        settings.setProvider(provider);
        ServerFactory.getInstance(this).setBaseUrl(provider.getAPIUrl());

        startActivity(new Intent(this, PairPhone.class));

//        startActivity(new Intent(this, UserLogin.class));
    }
}

