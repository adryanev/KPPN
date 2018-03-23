package com.circlenode.adryanekavandra.kppn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class NotifStake {

    @SerializedName("notifID")
    @Expose
    private String notifID;
    @SerializedName("nama_stake")
    @Expose
    private String namaStake;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("pengirim")
    @Expose
    private String pengirim;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;

    public String getNotifID() {
        return notifID;
    }

    public void setNotifID(String notifID) {
        this.notifID = notifID;
    }

    public String getNamaStake() {
        return namaStake;
    }

    public void setNamaStake(String namaStake) {
        this.namaStake = namaStake;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getPengirim() {
        return pengirim;
    }

    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
