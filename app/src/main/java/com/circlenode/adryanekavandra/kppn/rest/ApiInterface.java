package com.circlenode.adryanekavandra.kppn.rest;


import com.circlenode.adryanekavandra.kppn.responses.ResponseBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseDetailBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotif;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotifStake;
import com.circlenode.adryanekavandra.kppn.responses.ResponseStakeholder;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("video")
    Call<ResponseBerita> getAllBerita();

    @GET("video/notif")
    Call<ResponseNotif> getGlobalNotif();

    @GET("video/notifstake")
    Call<ResponseNotifStake> getStakeNotif();

    @FormUrlEncoded
    @POST("video/stake")
    Call<ResponseStakeholder> requestLogin(
            @Field("kode_stake") String kodeStake,
            @Field("password") String password
    );
    @GET("video/artikel")
    Call<ResponseDetailBerita> getDetailBerita(@Query("id") Integer idBerita);





}
