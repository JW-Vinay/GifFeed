package com.giffedup;

import android.content.Context;

import com.giffedup.api.Interceptor;
import com.giffedup.api.RestClient;
import com.giffedup.utils.SharedPrefs;

/**
 * Created by vinay-r on 8/12/15.
 */
public class ApplicationData {

    private RestClient mRestClient;
    private Interceptor mInterceptor;
    private SharedPrefs mSharedPrefs;

    private static ApplicationData mInstance;

    private ApplicationData() {

    }

    public static ApplicationData getInstance() {
        if(mInstance == null) {
            mInstance = new ApplicationData();
        }
        return mInstance;
    }

    public void init(Context context) {
        mInterceptor = new Interceptor();
        mRestClient = new RestClient(context);
        mSharedPrefs = new SharedPrefs(context);
    }

    public Interceptor getInterceptor() {
        return mInterceptor;
    }

    public RestClient getRestClient() {
        return mRestClient;
    }

    public SharedPrefs getSharedPrefs() {
        return mSharedPrefs;
    }
}
