package com.circlenode.adryanekavandra.kppn.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.circlenode.adryanekavandra.kppn.R;
import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.circlenode.adryanekavandra.kppn.receiver.AlarmReceiver;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.sqlite.DbHelper;
import com.circlenode.adryanekavandra.kppn.utils.SessionManager;
import com.circlenode.adryanekavandra.kppn.utils.SyncManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adryan Eka Vandra on 3/22/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    ApiInterface apiService;
    SessionManager sessionManager;
    ImageView imageView;
    DbHelper db;
    SyncManager syncManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
       db = new DbHelper(getApplicationContext());
       syncManager = new SyncManager(SplashActivity.this);
        retriveGlobalNotification();


        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        imageView = (ImageView)findViewById(R.id.gambarKPPN);

        changeStatusBarColor();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Boolean isLoggedIn = sessionManager.isLoggedIn();

                if(isLoggedIn){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }

            }
        },3000);

    }

    private void retriveGlobalNotification() {

        syncManager.syncAll();
        setGlobalNotif();
        if(sessionManager.isLoggedIn()){
            setStakeNotif();
        }


    }

    private void setStakeNotif() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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

            Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
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
            PendingIntent broadcast = PendingIntent.getBroadcast(SplashActivity.this, Integer.parseInt(notif.getNotifID()) + 1000, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent broadcast1 = PendingIntent.getBroadcast(SplashActivity.this, Integer.parseInt(notif.getNotifID()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, three.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast1);
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID())));
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
        }
    }

    private void setGlobalNotif() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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

            Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
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
            PendingIntent broadcast = PendingIntent.getBroadcast(SplashActivity.this, Integer.parseInt(notif.getNotifID()) + 1000, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent broadcast1 = PendingIntent.getBroadcast(SplashActivity.this, Integer.parseInt(notif.getNotifID()), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HALF_DAY, broadcast);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, three.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, broadcast1);
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID())));
            Log.i("MAIN_ACTIVITY", "BERHASIL SET NOTIF " + String.valueOf(Integer.parseInt(notif.getNotifID()) + 1000));
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
