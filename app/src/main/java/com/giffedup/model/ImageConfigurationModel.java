package com.giffedup.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by vinay-r on 8/10/15.
 */
public class ImageConfigurationModel implements Parcelable {

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

    protected ImageConfigurationModel(ParseObject parseObject) {
        if(parseObject.isDataAvailable()) {
            setData(parseObject);
        } else {
            parseObject.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    setData(object);
                    System.out.println("url: " + getUrl());
                }
            });
        }


    }

    public static final ImageConfigurationModel getConfiguration(ParseObject parseObject) {
        return new ImageConfigurationModel(parseObject);
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

    public ParseObject createParseObject() {
        ParseObject object = new ParseObject("Config");
        object.put("url", mUrl);
        object.put("height", mHeight);
        object.put("width", mWidth);

        return object;
    }

    public void setData(ParseObject object) {
        mUrl = object.getString("url");
        mWidth = object.getInt("width");
        mHeight = object.getInt("height");
    }

    public String getUrl() {
        return mUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return getHeight();
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
    }

    public void setmHeight(int height) {
        this.mHeight = mHeight;
    }

    public void setmUrl(String url) {
        this.mUrl = url;
    }
}
