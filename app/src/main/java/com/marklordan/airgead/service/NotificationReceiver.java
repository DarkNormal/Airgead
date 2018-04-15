package com.marklordan.airgead.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.marklordan.airgead.R;
import com.marklordan.airgead.ui.main.MainActivity;

import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String DEBUG_TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainactivityIntent = new Intent(context, MainActivity.class);
        PendingIntent resultIntent = PendingIntent.getActivity(context, 0, mainactivityIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(DEBUG_TAG, "NotificationReceiver: received");
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Log your expenses with Airgead")
                .setContentText("Don't forget to log your expenses for today!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }
}
