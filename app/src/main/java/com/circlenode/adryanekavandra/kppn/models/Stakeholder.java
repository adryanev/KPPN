package com.circlenode.adryanekavandra.kppn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class Stakeholder {
    @SerializedName("stakeID")
    @Expose
    private String stakeID;
    @SerializedName("kode_stake")
    @Expose
    private String kodeStake;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("nama_stake")
    @Expose
    private String namaStake;
    @SerializedName("email_stake")
    @Expose
    private String emailStake;

    public String getStakeID() {
        return stakeID;
    }

    public void setStakeID(String stakeID) {
        this.stakeID = stakeID;
    }

    public String getKodeStake() {
        return kodeStake;
    }

    public void setKodeStake(String kodeStake) {
        this.kodeStake = kodeStake;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaStake() {
        return namaStake;
    }

    public void setNamaStake(String namaStake) {
        this.namaStake = namaStake;
    }

    public String getEmailStake() {
        return emailStake;
    }

    public void setEmailStake(String emailStake) {
        this.emailStake = emailStake;
    }

}
