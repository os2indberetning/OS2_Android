package it_minds.dk.eindberetningmobil_android;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by kasper on 27-06-2015.
 * the applications main class. we use this only to do overall things that are required at application start.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
