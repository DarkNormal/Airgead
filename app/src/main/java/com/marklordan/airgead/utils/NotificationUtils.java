package com.marklordan.airgead.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.marklordan.airgead.service.NotificationReceiver;

import java.util.Calendar;

/**
 * Created by Mark on 13/05/2018.
 */

public class NotificationUtils {

    public static void scheduleNotification(Context context){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 20);
        PendingIntent pendingIntent;
        Intent intent = new Intent(context, NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //TODO - alarm repeats very regularly as a test for development, for release it must use cal
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 8000, pendingIntent);
    }
}
