package it_minds.dk.eindberetningmobil_android.views;

import android.os.Bundle;
import android.widget.ListView;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseProvidedDialog;
import it_minds.dk.eindberetningmobil_android.baseClasses.BaseReportActivity;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 01-09-2015.
 */
public class MissingTripActivity extends BaseReportActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missing_trips_view);
        listView = getViewById(R.id.missing_trips_listview);
        refreshData();
    }

    private void refreshData(){
        //load from settings
        //MainSettings.getInstance(this).

    }
}
