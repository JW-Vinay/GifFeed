package com.giffedup.api;

import com.giffedup.ApplicationData;

import retrofit.RequestInterceptor;

/**
 * Created by vinay-r on 8/10/15.
 */
public class Interceptor implements RequestInterceptor {

    private final String API_KEY = "api_key";

    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam(API_KEY, ApplicationData.getInstance().getSharedPrefs().getGifApiKey());
    }
}
