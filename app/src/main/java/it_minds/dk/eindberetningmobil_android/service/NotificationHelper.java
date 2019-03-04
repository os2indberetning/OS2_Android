/*
 * Copyright (c) OS2 2016.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package it_minds.dk.eindberetningmobil_android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import it_minds.dk.eindberetningmobil_android.R;
import it_minds.dk.eindberetningmobil_android.views.MonitoringActivity;

/**
 * simple notification helper
 */
public class NotificationHelper {

    private static final String NOTIFICATION_CHANNEL_DEFAULT_ID = "notification_channel_default";

    public static final int ID = 10;

    /**
     * Creates a notification.
     * @param context
     * @param title
     * @param content
     * @return
     */
    public static Notification createNotification(Context context, String title, String content) {
        Intent intent = new Intent(context, MonitoringActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            String notificationChannelName = context.getString(R.string.app_name);

            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_DEFAULT_ID,
                    notificationChannelName,
                    importance);

            NotificationManager notificationManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        int smallIcon;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            smallIcon = R.drawable.ic_sil;
        } else {
            smallIcon = R.mipmap.ic_launcher;
        }

        return new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_DEFAULT_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setNumber(0)
                .setContentIntent(pendingIntent)
                .setLargeIcon(icon)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
    }


}
