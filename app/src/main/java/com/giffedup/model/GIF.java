package com.giffedup.model;

import android.os.Parcel;

/**
 * Created by vinay-r on 8/10/15.
 */
public class GIF extends Content {

    public GIF() {
        super();
        setContentType("GIF");
    }

    public GIF(Parcel in) {
        super(in);
    }

    public static final Creator<GIF> CREATOR = new Creator<GIF>() {
        @Override
        public GIF createFromParcel(Parcel in) {
            return new GIF(in);
        }

        @Override
        public GIF[] newArray(int size) {
            return new GIF[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
