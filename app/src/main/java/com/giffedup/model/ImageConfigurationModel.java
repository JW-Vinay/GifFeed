package com.giffedup.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by vinay-r on 8/10/15.
 */
@ParseClassName("Config")
public class ImageConfigurationModel extends ParseObject implements Parcelable {

    @SerializedName("width")
    private int mWidth;

    @SerializedName("height")
    private int mHeight;

    @SerializedName("url")
    private String mUrl;

    public ImageConfigurationModel() {

    }

    protected ImageConfigurationModel(Parcel in) {
        mWidth = in.readInt();
        mHeight = in.readInt();
        mUrl = in.readString();
    }

    public static final Creator<ImageConfigurationModel> CREATOR = new Creator<ImageConfigurationModel>() {
        @Override
        public ImageConfigurationModel createFromParcel(Parcel in) {
            return new ImageConfigurationModel(in);
        }

        @Override
        public ImageConfigurationModel[] newArray(int size) {
            return new ImageConfigurationModel[size];
        }
    };

    public String getUrl() {
        if (getString("url") == null)
            return mUrl;
        return getString("url");
    }

    public int getWidth() {
        if (getInt("width") == 0)
            return mWidth;
        return getInt("width");
    }

    public int getHeight() {
        if (getInt("height") == 0)
            return getHeight();
        return getInt("height");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
        dest.writeString(mUrl);
    }

    public void setmWidth(int width) {
        this.mWidth = width;
        put("width", width);
    }

    public void setmHeight(int height) {
        this.mHeight = mHeight;
        put("height", height);
    }

    public void setmUrl(String url) {
        this.mUrl = url;
        put("url", url);
    }
}
