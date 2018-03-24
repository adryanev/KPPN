package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.NotifStake;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ResponseNotifStake {

    @SerializedName("data")
    @Expose
    private List<NotifStake> data = null;

    public List<NotifStake> getData() {
        return data;
    }

    public void setData(List<NotifStake> data) {
        this.data = data;
    }

}
