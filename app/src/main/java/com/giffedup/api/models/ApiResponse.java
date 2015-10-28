package com.giffedup.api.models;

import com.giffedup.model.Content;
import com.giffedup.model.Pages;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay-r on 8/10/15.
 */
public class ApiResponse {

    @SerializedName("data")
    private ArrayList<? extends Content> mContentList;

    @SerializedName("pagination")
    private Pages mPages;

    @SerializedName("meta")
    private HttpResponse mHttResponse;


    public Pages getPages() {
        return mPages;
    }

    public HttpResponse getHttResponse() {
        return mHttResponse;
    }

    public List<? extends Content> getContentList() {

        return mContentList;
    }
}
