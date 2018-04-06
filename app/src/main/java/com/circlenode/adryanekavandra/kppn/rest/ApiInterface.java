package com.circlenode.adryanekavandra.kppn.rest;


import com.circlenode.adryanekavandra.kppn.responses.ResponseBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseDetailBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotif;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotifStake;
import com.circlenode.adryanekavandra.kppn.responses.ResponseStakeholder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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

    @FormUrlEncoded
    @POST("video/edit-profil")
    Call<ResponseBody> editProfil(
            @Field("kodeStake") String kodeStake,
            @Field("namaStake") String namaStake,
            @Field("password") String password,
            @Field("email") String email
    );




}
