package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ResponseStakeholder {
    @SerializedName("data")
    @Expose
    private List<Stakeholder> data = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public List<Stakeholder> getData() {
        return data;
    }

    public void setData(List<Stakeholder> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}
