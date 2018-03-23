package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.DetailBerita;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ResponseDetailBerita {

    @SerializedName("data")
    @Expose
    private List<DetailBerita> data = null;

    public List<DetailBerita> getData() {
        return data;
    }

    public void setData(List<DetailBerita> data) {
        this.data = data;
    }
}
