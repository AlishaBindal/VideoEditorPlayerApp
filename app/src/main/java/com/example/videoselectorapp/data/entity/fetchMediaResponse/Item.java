
package com.example.videoselectorapp.data.entity.fetchMediaResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlShareable")
    @Expose
    private String urlShareable;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;
    @SerializedName("is")
    @Expose
    private String is;
    @SerializedName("locationId")
    @Expose
    private int locationId;
    @SerializedName("totalLikes")
    @Expose
    private int totalLikes;
    @SerializedName("totalComments")
    @Expose
    private int totalComments;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("caption")
    @Expose
    private String caption;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlShareable() {
        return urlShareable;
    }

    public void setUrlShareable(String urlShareable) {
        this.urlShareable = urlShareable;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
