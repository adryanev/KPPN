package com.circlenode.adryanekavandra.kppn.responses;

import com.circlenode.adryanekavandra.kppn.models.Berita;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Adryan Eka Vandra on 3/23/2018.
 */

public class ResponseBerita {

    @SerializedName("data")
    @Expose
    private List<Berita> data = null;

    public List<Berita> getData() {
        return data;
    }

    public void setData(List<Berita> data) {
        this.data = data;
    }
}
