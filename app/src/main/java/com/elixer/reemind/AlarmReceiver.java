package com.elixer.reemind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Admin on 7/30/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {


    // public static String NOTIFICATION_ID = "notification-id";
    // public static String NOTIFICATION = "notification";


    @Override
    public void onReceive(final Context context, Intent intent) {

       /* NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);

        */
        // WakeLocker wakelocker = new WakeLocker();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");

        wakeLock.acquire(30000);
        Intent myIntent = new Intent(context, MainActivity.class);
        intent.putExtra("tab", "1");
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                         .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                         R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setContentTitle("Event Reminder")
                        .setContentText("5 minutes to the Event")
                         .setSound(Uri.parse("android.resource://"
                                 + context.getPackageName() + "/" + R.raw.bells))
                            .setVibrate(new long[] { 0,1000,1000, 1000, 1000 })
                                .setLights(Color.YELLOW, 3000, 3000)

                        .setContentIntent(pIntent);


        //Gets an instance of the NotificationManager service//
                //mBuilder.setOnlyAlertOnce(true);
        NotificationManager mNotificationManager =

                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
       notification.flags |= Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;


        mNotificationManager.notify(001,notification);


    }


    }
