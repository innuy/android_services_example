package com.example.innuy.services_sample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created on 29/7/16.
 * @author INNUY
 */
public class BoundService extends Service {
    private static final int NOTIFICATION_REQ_CODE = 1003;

    NotificationCompat.Builder notificationBuilder;

    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationBuilder = getNotificationBuilder("Bound service created",
                "The service was created, it will stay alive until all activities are unbound",
                new Intent(getApplicationContext(), MainActivity.class), NOTIFICATION_REQ_CODE);

        notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_REQ_CODE, notificationBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // It does not return a custom binder because it is not used,
        // it is just to be able to call the service connection
        return new Binder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        notificationBuilder = getNotificationBuilder("Bound service destroyed",
                "All objects were unbound",
                new Intent(getApplicationContext(), MainActivity.class), NOTIFICATION_REQ_CODE);

        notificationManager.notify(NOTIFICATION_REQ_CODE, notificationBuilder.build());
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
