package com.example.weatherinfo.validate;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by server on 8/26/2016.
 */
public  class Validate {

    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connection=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =connection.getActiveNetworkInfo();
        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }
    public static String accessToken="";
}
