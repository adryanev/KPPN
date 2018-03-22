package com.circlenode.adryanekavandra.kppn.rest;


import com.circlenode.adryanekavandra.kppn.responses.ResponseNotif;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {

    @GET("video/notif")
    Call<ResponseNotif> getNotif();
}
