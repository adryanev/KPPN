package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.Stakeholder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseStake {

    @SerializedName("data")
    @Expose
    private List<Stakeholder> stakeholderList = null;

    public List<Stakeholder> getStakeHolder() {
        return stakeholderList;
    }

    public void setStakeholder(List<Stakeholder> stakeholderList) {
        this.stakeholderList = stakeholderList;
    }

}
