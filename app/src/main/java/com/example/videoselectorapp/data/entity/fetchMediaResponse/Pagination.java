
package com.example.videoselectorapp.data.entity.fetchMediaResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("nextToken")
    @Expose
    private String nextToken;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

}
