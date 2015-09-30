package com.giffedup.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay-r on 8/10/15.
 */
public class Pages {

    @SerializedName("count")
    private int mCount;

    @SerializedName("offset")
    private int mOffset;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }
}
