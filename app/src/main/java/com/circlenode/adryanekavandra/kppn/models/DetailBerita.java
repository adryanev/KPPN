package com.circlenode.adryanekavandra.kppn.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class DetailBerita {
    @SerializedName("arID")
    @Expose
    private String arID;
    @SerializedName("arJudul")
    @Expose
    private String arJudul;
    @SerializedName("arContent")
    @Expose
    private String arContent;
    @SerializedName("arImage")
    @Expose
    private String arImage;
    @SerializedName("arKategoriID")
    @Expose
    private String arKategoriID;
    @SerializedName("arAkses")
    @Expose
    private String arAkses;
    @SerializedName("arAktif")
    @Expose
    private String arAktif;
    @SerializedName("arTanggal")
    @Expose
    private String arTanggal;

    public String getArID() {
        return arID;
    }

    public void setArID(String arID) {
        this.arID = arID;
    }

    public String getArJudul() {
        return arJudul;
    }

    public void setArJudul(String arJudul) {
        this.arJudul = arJudul;
    }

    public String getArContent() {
        return arContent;
    }

    public void setArContent(String arContent) {
        this.arContent = arContent;
    }

    public String getArImage() {
        return arImage;
    }

    public void setArImage(String arImage) {
        this.arImage = arImage;
    }

    public String getArKategoriID() {
        return arKategoriID;
    }

    public void setArKategoriID(String arKategoriID) {
        this.arKategoriID = arKategoriID;
    }

    public String getArAkses() {
        return arAkses;
    }

    public void setArAkses(String arAkses) {
        this.arAkses = arAkses;
    }

    public String getArAktif() {
        return arAktif;
    }

    public void setArAktif(String arAktif) {
        this.arAktif = arAktif;
    }

    public String getArTanggal() {
        return arTanggal;
    }

    public void setArTanggal(String arTanggal) {
        this.arTanggal = arTanggal;
    }
}
