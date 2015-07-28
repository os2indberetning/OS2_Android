package it_minds.dk.eindberetningmobil_android.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.constants.IntentIndexes;
import it_minds.dk.eindberetningmobil_android.interfaces.OnLocationChangedCallback;
import it_minds.dk.eindberetningmobil_android.location.GpsMonitor;
import it_minds.dk.eindberetningmobil_android.location.LocationMgr;
import it_minds.dk.eindberetningmobil_android.models.DrivingReport;
import it_minds.dk.eindberetningmobil_android.settings.MainSettings;

/**
 * Created by kasper on 25-07-2015.
 * background service handling the gps location calculations. This service is designed to run in the background, as a foreground service (with a notification).
 */
public class MonitoringService extends Service implements OnLocationChangedCallback {

    private static final int SERVICE_ID = 1;

    //<editor-fold desc="Service commands (indexes)">
    private static final String COMMAND_PAUSE_RESUME = "COMMAND_PAUSE_RESUME";
    private static final String COMMAND_START = "COMMAND_START";
    private static final String COMMAND_SEND_STATUS = "COMMAND_SEND_STATUS";
    //</editor-fold>

    private boolean isListening = false;
    private LocationMgr locMgr;

    private GpsMonitor gpsMonitor;

    private ResultReceiver callback;

    private MonitoringServiceReport manager;

    private static boolean haveBeenStarted = false;

    /**
     * Gets called when we start.
     *
     * @param intent
     */
    protected void onStartFromIntent(Intent intent) {
        if (manager == null) {
            manager = new MonitoringServiceReport(intent, this);
            locMgr = LocationMgr.getInstance(this);
            locMgr.registerOnLocationChanged(this);
            isListening = true;
            callback = intent.getParcelableExtra(IntentIndexes.CALLBACK_INDEX);
            updateNotification(getString(R.string.notification_active_title), getString(R.string.notification_active_message) + manager.getCurrentDistanceInKm() + " km");
        } else {
            callback = intent.getParcelableExtra(IntentIndexes.CALLBACK_INDEX);
            if (manager.createUiStatus() != null) {
                sendUiUpdate(manager.createUiStatus());
            }
        }
        gpsMonitor = new GpsMonitor(onGpsChanged);
        gpsMonitor.startListening(this);
        haveBeenStarted = true;
    }

    private void updateNotification(String title, String content) {
        Notification notification = NotificationHelper.createNotification(this, title, content);
        startForeground(SERVICE_ID, notification);
    }

    @Override
    public void onDestroy() {
        haveBeenStarted = false;
        MainSettings.getInstance(this).setServiceClosed(true);
        locMgr.unRegisterOnLocationChanged(this);
        if (gpsMonitor != null) {
            gpsMonitor.stopListening(this);
        }
        Log.e("temp", "service killed");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public boolean stopService(Intent name) {
        super.stopService(name);
        stopForeground(true);
        if (locMgr != null) {
            locMgr.unRegisterOnLocationChanged(this);
        }
        callback.send(Activity.RESULT_OK, manager.createNotificationBundle());
        return true;
    }

    /**
     * Handles commands to this service.
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (intent != null) {
            if (intent.hasExtra(COMMAND_START)) {
                onStartFromIntent(intent);
            } else if (intent.hasExtra(COMMAND_PAUSE_RESUME)) {
                if (isListening) {
                    pause();
                } else {
                    if (gpsMonitor.isGpsActive()) {
                        sendGpsWorking();
                        resume();
                    } else {
                        sendGpsError();
                    }
                }
            } else if (intent.hasExtra(COMMAND_SEND_STATUS)) {
                sendStatus();
            }
        }
        MainSettings.getInstance(this).setServiceClosed(false);
        return START_STICKY;

    }

    /**
     * sends updates to the acitvity about the state / ui
     *
     * @param model
     */
    public void sendUiUpdate(UiStatusModel model) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(IntentIndexes.UI_INDEX, model);
        if (callback != null) {
            callback.send(Activity.RESULT_OK, bundle);
        }
    }

    /**
     * Sends the current listening status to the activity.
     */
    private void sendStatus() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentIndexes.STATUS_INDEX, isListening);
        if (callback != null) {
            callback.send(Activity.RESULT_OK, bundle);
        }
    }

    private void resume() {
        updateNotification(getString(R.string.notification_resuming_title), getString(R.string.notification_resuming_body));
        isListening = true;
        locMgr.registerOnLocationChanged(this);
        callback.send(Activity.RESULT_OK, manager.createNotificationBundle());
        sendStatus();
    }

    private void pause() {
        if (locMgr != null) {
            locMgr.unRegisterOnLocationChanged(this);
        }
        manager.pause();
        callback.send(Activity.RESULT_OK, manager.createNotificationBundle());
        isListening = false;
        sendStatus();
        updateNotification(getString(R.string.notification_stopped_title), getString(R.string.notification_stopped_body));
    }

    public MonitoringService() {
        super();
    }

    /**
     * When we get a new location
     *
     * @param location
     */
    @Override
    public void onNewLocation(Location location) {
        if (manager != null) {
            manager.addLocation(location);
            if (callback != null) {
                callback.send(Activity.RESULT_OK, manager.createNotificationBundle());
            }
            updateNotification(getString(R.string.notification_active_title), getString(R.string.notification_active_message) + manager.getCurrentDistanceInKm() + " km");
        }
    }

    //<editor-fold desc="Service commands">
    public static void getListeningState(Context context) {
        Intent intent = createServiceIntent(context, COMMAND_SEND_STATUS);
        context.startService(intent);
    }

    public static void pauseResumeListening(Context context) {
        Intent intent = createServiceIntent(context, COMMAND_PAUSE_RESUME);// new Intent(context, MonitoringService.class);
        context.startService(intent);
    }

    private static Intent createServiceIntent(Context context, String command) {
        Intent intent = new Intent(context, MonitoringService.class);
        intent.putExtra(command, true);
        return intent;
    }

    public static void stopService(Context context) {
        Intent intent = new Intent(context, MonitoringService.class);
        context.stopService(intent);
    }

    public static void startService(Context context, MonitoringReciver.Receiver receiver, DrivingReport report) {
        Intent intent = createServiceIntent(context, COMMAND_START);
        intent.putExtra(IntentIndexes.CALLBACK_INDEX, new MonitoringReciver(new Handler()).setReceiver(receiver));
        intent.putExtra(IntentIndexes.DATA_INDEX, report);
        context.startService(intent);
    }

    public boolean isListening() {
        return isListening;
    }

    public void sendError() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentIndexes.ERROR_INDEX, true);
        callback.send(Activity.RESULT_OK, bundle);
    }
    //</editor-fold>

    private Runnable onGpsChanged = new Runnable() {
        @Override
        public void run() {
            if (gpsMonitor.isGpsActive()) {//we just became active
                sendGpsWorking();
            } else { //we just stopped the gps !??!?
                sendGpsError();
                pause();
            }
        }
    };

    private void sendGpsWorking() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentIndexes.WORKING_GPS_INDEX, true);
        callback.send(Activity.RESULT_OK, bundle);
    }

    private void sendGpsError() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentIndexes.ERROR_GPS_INDEX, true);
        callback.send(Activity.RESULT_OK, bundle);
    }

    /**
     * tells if the service itself belives it has been started (as a guard against middle crashes of the app , and the prefs not updated).
     *
     * @return
     */
    public static boolean isServiceActive() {
        return haveBeenStarted;
    }
}
