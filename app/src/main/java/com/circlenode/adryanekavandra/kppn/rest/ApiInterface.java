package com.circlenode.adryanekavandra.kppn.rest;


import com.circlenode.adryanekavandra.kppn.responses.ResponseBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseDetailBerita;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotif;
import com.circlenode.adryanekavandra.kppn.responses.ResponseNotifStake;
import com.circlenode.adryanekavandra.kppn.responses.ResponsePembendaharaan;
import com.circlenode.adryanekavandra.kppn.responses.ResponseStake;
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

    @GET("pembendaharaan/get-stake")
    Call<ResponseStake> getAllStakeholder();
    @GET("pembendaharaan/index")
    Call<ResponsePembendaharaan> getPembendaharaan(@Query("id") Integer id);

    @FormUrlEncoded
    @POST("pembendaharaan/create")
    Call<ResponseBody> setPembendaharaan(@Field("nama") String nama,
                                                   @Field("jabatan") String tempatLahir,
                                                   @Field("alamat") String alamat,
                                                   @Field("email") String email,
                                                   @Field("no_hp") String noHP,
                                                   @Field("id_stakeholder") Integer idStakeholder);
    @FormUrlEncoded
    @POST("pembendaharaan/update")
    Call<ResponseBody> updatePembendaharaan(@Query("id") Integer idBerita,
                                                      @Field("nama") String nama,
                                                   @Field("jabatan") String tempatLahir,
                                                   @Field("alamat") String alamat,
                                                   @Field("email") String email,
                                                   @Field("no_hp") String noHP,
                                                   @Field("id_stakeholder") Integer idStakeholder);

    @GET("pembendaharaan/delete")
    Call<ResponseBody> deletePembendaharaan(@Query("id") Integer idPembendaharaan);

    @GET("pembendaharaan/view")
    Call<ResponsePembendaharaan> getBendahara(@Query("id") Integer id);



}
