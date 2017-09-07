package com.elixer.reemind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Admin on 7/30/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {


    // public static String NOTIFICATION_ID = "notification-id";
    // public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(Context context, Intent intent) {

       /* NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

        */
        WakeLocker wakelocker = new WakeLocker();
        wakelocker.acquire(context);
        Intent myIntent = new Intent(context, MainActivity.class);
        intent.putExtra("tab", "1");
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .setContentTitle("Event Reminder")
                        .setContentText("5 minutes to the Event")
                         .setDefaults(Notification.DEFAULT_SOUND)
                        .setContentIntent(pIntent);

          //Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(001, mBuilder.build());
        wakelocker.release();
        Log.d("BUilding","alarm");
    }
}