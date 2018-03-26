package com.circlenode.adryanekavandra.kppn.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Notif {

    @SerializedName("notifID")
    @Expose
    private String notifID;

    @SerializedName("notifNama")
    @Expose
    private String notifNama;

    @SerializedName("notifStakeHolder")
    @Expose
    private String notifStakeHolder;

    @SerializedName("notifStartEnd")
    @Expose
    private String notifStartEnd;

    @SerializedName("notifPengirim")
    @Expose
    private String notifPengirim;
    @SerializedName("notifPesan")
    @Expose
    private String notifPesan;

    public String getNotifID() {
        return notifID;
    }

    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    public String getNotifNama() {
        return notifNama;
    }

    public void setNotifNama(String notifNama) {
        this.notifNama = notifNama;
    }

    public String getNotifStakeHolder() {
        return notifStakeHolder;
    }

    public void setNotifStakeHolder(String notifStakeHolder) {
        this.notifStakeHolder = notifStakeHolder;
    }

    public String getNotifStartEnd() {
        return notifStartEnd;
    }

    public void setNotifStartEnd(String notifStartEnd) {
        this.notifStartEnd = notifStartEnd;
    }

    public String getNotifPengirim() {
        return notifPengirim;
    }

    public void setNotifPengirim(String notifPengirim) {
        this.notifPengirim = notifPengirim;
    }

    public String getNotifPesan() {
        return notifPesan;
    }

    public void setNotifPesan(String notifPesan) {
        this.notifPesan = notifPesan;
    }

}

