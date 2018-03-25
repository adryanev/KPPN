package com.circlenode.adryanekavandra.kppn.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotif;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotifStake;
import com.circlenode.adryanekavandra.kppn.rest.ApiClient;
import com.circlenode.adryanekavandra.kppn.rest.ApiInterface;
import com.circlenode.adryanekavandra.kppn.sqlite.DbHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adryanev on 17/10/17.
 */

public class SyncManager extends ContextWrapper {

    private ProgressDialog pb;
    private static final String TAG = SyncManager.class.getSimpleName();


    private DbHelper db;
    public SyncManager(Context context){
        super(context);
        this.db = new DbHelper(this);
    }


    public void syncAll(){
        syncNotif();
        syncNotifStake();
    }

    private void syncNotifStake() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseNotifStake> call = apiService.getStakeNotif();
        call.enqueue(new Callback<ResponseNotifStake>() {
            @Override
            public void onResponse(Call<ResponseNotifStake> call, Response<ResponseNotifStake> response) {

                if(response.isSuccessful()){
                    List<NotifStake> notifs = response.body().getData();
                    Log.d(TAG,"Succes receiving: "+notifs.size());
                    db.truncateTable("notifstake");
                    for (NotifStake notif: notifs) {
                        db.insertNotifStake(notif);
                        Log.d(TAG,"Insert notifstake "+notif.getNotifID());

                    }
                }
                else{
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    Log.d(TAG,"Gagal, Error Code ="+statusCode);
                }

            }

            @Override
            public void onFailure(Call<ResponseNotifStake> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private void syncNotif() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseNotif> call = apiService.getGlobalNotif();
        call.enqueue(new Callback<ResponseNotif>() {
            @Override
            public void onResponse(Call<ResponseNotif> call, Response<ResponseNotif> response) {

                if(response.isSuccessful()){
                    List<Notif> notifs = response.body().getData();
                    Log.d(TAG,"Succes receiving: "+notifs.size());
                    db.truncateTable("notif");
                    for (Notif notif: notifs) {
                        db.insertNotif(notif);
                        Log.d(TAG,"Insert Notif "+notif.getNotifID());

                    }
                }
                else{
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    Log.d(TAG,"Gagal, Error Code ="+statusCode);
                }

            }

            @Override
            public void onFailure(Call<ResponseNotif> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }



}
