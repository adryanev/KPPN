package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.Notif;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseNotif {
    @SerializedName("data")
    @Expose
    private List<Notif> data = null;

    public List<Notif> getData() {
        return data;
    }

    public void setData(List<Notif> data) {
        this.data = data;
    }
}
