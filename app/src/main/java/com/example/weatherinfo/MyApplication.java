package com.example.weatherinfo;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by server on 8/26/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
