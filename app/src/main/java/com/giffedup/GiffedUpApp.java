package com.giffedup;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by vinay-r on 8/10/15.
 */
public class GiffedUpApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationData.getInstance().init(this);
        Fresco.initialize(this);
    }


}
