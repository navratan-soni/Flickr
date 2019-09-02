package com.nuffwritten.flickr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navratan on 2019-09-03
 */
public class SearchResultModel {

    @SerializedName("photos")
    @Expose
    private PhotosModel photos;
    @SerializedName("stat")
    @Expose
    private String stat;

    public PhotosModel getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosModel photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}