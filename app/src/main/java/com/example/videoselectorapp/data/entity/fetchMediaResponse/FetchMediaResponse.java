
package com.example.videoselectorapp.data.entity.fetchMediaResponse;

import com.example.videoselectorapp.data.entity.CommonResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchMediaResponse extends CommonResponse {
    public static final String IS_IMAGE = "IMG";
    public static final String IS_VIDEO = "VID";

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private Data data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
