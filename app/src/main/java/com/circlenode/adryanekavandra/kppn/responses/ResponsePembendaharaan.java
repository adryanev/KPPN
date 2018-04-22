package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.Bendahara;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePembendaharaan {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Bendahara> bendaharaList = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Bendahara> getBendaharaList() {
        return bendaharaList;
    }

    public void setBendaharaList (List<Bendahara> bendaharaList) {
        this.bendaharaList = bendaharaList;
    }
}
