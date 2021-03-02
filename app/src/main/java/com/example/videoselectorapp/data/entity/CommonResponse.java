package com.example.videoselectorapp.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Common Response
 */
public class CommonResponse {

    @SerializedName("error")
    private String error;
    @SerializedName("flag")
    private int flag;

    /**
     * get error
     *
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * get flag
     *
     * @return flag
     */
    public int getFlag() {
        return flag;
    }

}
