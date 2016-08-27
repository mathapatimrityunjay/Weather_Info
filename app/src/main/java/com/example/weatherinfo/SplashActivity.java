package com.example.weatherinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;


public class SplashActivity extends Activity {
public static  int SPLASH_TIME_OUT=1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
             @Override
            public void run() {
               startActivity(new Intent(SplashActivity.this, MainActivity.class));
              finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
