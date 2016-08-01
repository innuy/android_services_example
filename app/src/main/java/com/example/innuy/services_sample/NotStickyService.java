package com.example.innuy.services_sample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by diegoinsua on 29/7/16.
 */

public class NotStickyService extends Service {

    private static final int DELAY = 10000;
    private static final int NOTIFICATION_REQ_CODE = 1002;

    NotificationCompat.Builder notificationBuilder;

    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationBuilder = getNotificationBuilder("Long task finished",
                "This task takes 10s to finish",
                new Intent(getApplicationContext(), MainActivity.class), NOTIFICATION_REQ_CODE);

        notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                notificationManager.notify(NOTIFICATION_REQ_CODE, notificationBuilder.build());
                stopSelf();
            }
        }, DELAY);

        Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
    }

    public NotificationCompat.Builder getNotificationBuilder(String title, String text,
                                                             Intent activityIntent,
                                                             int requestCode) {
        // Creates the notification builder
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setAutoCancel(true);

        PendingIntent activityPendingIntent = PendingIntent.getActivity(this,
                requestCode, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(activityPendingIntent);

        return notificationBuilder;
    }
}
