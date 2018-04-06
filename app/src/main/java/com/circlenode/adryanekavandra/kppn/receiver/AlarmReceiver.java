package com.circlenode.adryanekavandra.kppn.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.activities.MainActivity;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;


/**
 * Created by adryanev on 15/03/18.
 */

public class AlarmReceiver  extends BroadcastReceiver{
    private static final String CHANNEL_ID = " com.example.ahmadfauzirahman.project_kppn.channelId";
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        String pengirm = intent.getStringExtra("notifNama");
        String pesan = intent.getStringExtra("notifPesan");
        int count = intent.getIntExtra("requestCode",0);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(count, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder.setContentTitle(pengirm)
                .setContentText(pesan)
                .setTicker("Pesan baru dari KPPN")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Pengingat Jadwal",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(count, notification);
    }
}
