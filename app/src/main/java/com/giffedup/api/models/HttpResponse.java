package com.giffedup.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay-r on 8/10/15.
 */
public class HttpResponse {

    @SerializedName("status")
    private int mStatus;

    @SerializedName("msg")
    private String mMessage;

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
