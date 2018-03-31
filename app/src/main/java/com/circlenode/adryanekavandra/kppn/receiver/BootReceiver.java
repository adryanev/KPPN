package com.circlenode.adryanekavandra.kppn.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.circlenode.adryanekavandra.kppn.activities.SplashActivity;
import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.circlenode.adryanekavandra.kppn.sqlite.DbHelper;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class BootReceiver extends BroadcastReceiver {
    SessionManager sessionManager;
    DbHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(context, WakefulReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            sessionManager = new SessionManager(context);
            db = new DbHelper(context);
            retriveGlobalNotification(context);
        }
    }

    private void retriveGlobalNotification(Context context) {
        setGlobalNotif(context);
        if(sessionManager.isLoggedIn()){
            setStakeNotif(context);
        }
    }
    private void setStakeNotif(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String,String> user = sessionManager.getUserDetail();
        List<NotifStake> list = db.getAllValidNotifStake(Integer.parseInt(user.get("stakeKode")));
        for (NotifStake notif : list) {
            Date c = Calendar.getInstance().getTime();
            Log.i("MAIN_ACTIVITY", "Current time => " + c);

            Date myDate = null;
            try {
                myDate = simpleDateFormat.parse(notif.getTanggal());
            } catch (ParseException e) {
                e.printStackTrace();

            }
            calendar.setTime(myDate);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Intent notificationIntent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
            Calendar three = Calendar.getInstance();
            three.setTime(calendar.getTime());
            three.add(Calendar.DAY_OF_YEAR, -3);

            Date minusThree = three.getTime();

            calendar.add(Calendar.DAY_OF_YEAR, -7);
            Date newDate = calendar.getTime();
            String result = simpleDateFormat.format(newDate);
            notificationIntent.putExtra("notifNama", notif.getPengirim());
            notificationIntent.putExtra("notifPesan", notif.getPesan());
            notificationIntent.putExtra("requestCode", Integer.parseInt(notif.getNotifID()) + 1000);
            Log.i("MAIN_ACTIVITY", "Set INTENT " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
            PendingIntent broadcast = PendingIntent.getBroadcast(context, Integer.parseInt(notif.getNotifID()) + 1000, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent broadcast1 = PendingIntent.getBroadcast(context, Integer.parseInt(notif.getNotifID()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, three.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast1);
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID())));
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
        }
    }

    private void setGlobalNotif(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Notif> list = db.getValidNotif();
        for (Notif notif : list) {
            Date c = Calendar.getInstance().getTime();
            Log.i("MAIN_ACTIVITY", "Current time => " + c);

            Date myDate = null;
            try {
                myDate = simpleDateFormat.parse(notif.getNotifStartEnd());
            } catch (ParseException e) {
                e.printStackTrace();

            }
            calendar.setTime(myDate);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Intent notificationIntent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
            Calendar three = Calendar.getInstance();
            three.setTime(calendar.getTime());
            three.add(Calendar.DAY_OF_YEAR, -3);

            Date minusThree = three.getTime();

            calendar.add(Calendar.DAY_OF_YEAR, -7);
            Date newDate = calendar.getTime();
            String result = simpleDateFormat.format(newDate);
            notificationIntent.putExtra("notifNama", notif.getNotifNama());
            notificationIntent.putExtra("notifPesan", notif.getNotifPesan());
            notificationIntent.putExtra("requestCode", Integer.parseInt(notif.getNotifID()) + 1000);
            Log.i("MAIN_ACTIVITY", "Set INTENT " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
            PendingIntent broadcast = PendingIntent.getBroadcast(context, Integer.parseInt(notif.getNotifID()) + 1000, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent broadcast1 = PendingIntent.getBroadcast(context, Integer.parseInt(notif.getNotifID()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, three.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast1);
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID())));
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
        }
    }
}
