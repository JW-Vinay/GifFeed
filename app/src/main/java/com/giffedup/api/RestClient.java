package com.giffedup.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.giffedup.ApplicationData;
import com.giffedup.api.service.GifService;

import retrofit.RestAdapter;

/**
 * Created by vinay-r on 8/10/15.
 */
public class RestClient {

    private static final String BASE_URL = "http://api.giphy.com/";

    private GifService mGifService;
    private RestAdapter mRestAdapter;

    public RestClient(@NonNull Context context) {

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapterFactory(new TypedFactory())
//                .create();
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(ApplicationData.getInstance().getInterceptor())
                .build();
        mGifService = mRestAdapter.create(GifService.class);
    }

    public GifService getGifService() {
        return mGifService;
    }

}
