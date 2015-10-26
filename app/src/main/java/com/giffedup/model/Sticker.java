package com.giffedup.model;

import android.os.Parcel;

/**
 * Created by vinay-r on 8/10/15.
 */
public class Sticker extends Content {

    public Sticker() {
        super();
        setContentType("Sticker");
    }

    public Sticker(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
