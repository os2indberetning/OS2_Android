package it_minds.dk.eindberetningmobil_android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

/**
 * Created by kasper on 18-07-2015.
 */
public class NotificationHelper {

    public static final int ID = 10;

    public static void createNotification(Context context) {
        Intent intent = new Intent(context, MonitoringActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Kørsel aktiv");
        builder.setContentText("Du har indtil videre kørt xyz km");
        builder.setNumber(101);
        builder.setContentIntent(pendingIntent);
        builder.setTicker("Fancy Notification");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);
        builder.setLargeIcon(icon);
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        Notification notification = builder.build();
        NotificationManager notificationManger =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManger.notify(ID, notification);
    }

    public static void stopNotification(Context context){
        NotificationManager notificationManger =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManger.cancel(ID);
    }
}
