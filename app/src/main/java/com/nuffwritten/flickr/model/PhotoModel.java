package com.nuffwritten.flickr.model;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by navratan on 2019-09-03
 */
public class PhotoModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private int farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private int ispublic;
    @SerializedName("isfriend")
    @Expose
    private int isfriend;
    @SerializedName("isfamily")
    @Expose
    private int isfamily;

    private String url = null;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public String getImageUrl() {
        if(TextUtils.isEmpty(url)) {
           // https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
            url = new StringBuilder()
                    .append("https://farm")
                    .append(farm)
                    .append(".staticflickr.com/")
                    .append(server)
                    .append("/")
                    .append(id)
                    .append("_")
                    .append(secret)
                    .append(".jpg")
                    .toString();
        }
        return url;
    }

}
