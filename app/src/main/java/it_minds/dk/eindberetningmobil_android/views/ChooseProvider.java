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
import it_minds.dk.eindberetningmobil_android.server.ServerHandler;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 28-06-2015.
 */
public class ChooseProvider extends SimpleActivity {
    private MainSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = MainSettings.getInstance(this);
        if (settings.haveProvider()) {
            useProvider(settings.getProvider());
            return;
        }

        setContentView(R.layout.choose_provider_view);
        //set content to the list.
        final ListView lw = getViewById(R.id.choose_provider_view_list);
        ServerHandler.getInstance(this).getProviders(new ResultCallback<List<Provider>>() {
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
                Toast.makeText(ChooseProvider.this, "Der skete en fejl.... ", Toast.LENGTH_SHORT).show(); //TODO what should happen here.
            }
        });
    }

    private synchronized void useProvider(Provider provider) {
        Log.e("temp", "chose provider" + provider.getName());
        settings.setProvider(provider);
        ServerHandler.getInstance(this).setBaseUrl(provider.getApiUrl());
        startActivity(new Intent(this, PairPhone.class));
        finish();
    }
}

