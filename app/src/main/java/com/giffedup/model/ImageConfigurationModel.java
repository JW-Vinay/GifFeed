package com.giffedup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay-r on 8/10/15.
 */
public class ImageConfigurationModel {

    @SerializedName("width")
    private int mWidth;

    @SerializedName("height")
    private int mHeight;

    @SerializedName("url")
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return getHeight();
    }
}
