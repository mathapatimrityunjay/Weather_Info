package com.example.weatherinfo;

import android.app.Application;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherinfo.validate.Validate;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
private CallbackManager callbackManager;
    private AccessTokenTracker mTokenTracker;
    private Profile profile;
@InjectView(R.id.login_button)LoginButton loginBtn;
    @InjectView(R.id.txtFb)TextView txtResult;
    @InjectView(R.id.btnMaterial)Button btnToWeatherActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupActionTokenTracker();
        mTokenTracker.startTracking();
       initCallBackManager();
        try{   if(!Validate.accessToken.equalsIgnoreCase(""))
                btnToWeatherActivity.setVisibility(View.VISIBLE);
        }catch (Exception e){
            btnToWeatherActivity.setVisibility(View.GONE);
        }
   }
    @OnClick (R.id.btnMaterial)
     void gotoWeatherActivity(){
        startActivity(new Intent(MainActivity.this,WeatherActivity.class));
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {super.onActivityResult(requestCode,resultCode,data);
       try{  callbackManager.onActivityResult(requestCode,resultCode,data);
       }
       catch (Exception e){}
    }

public void initCallBackManager()
{
    if(!Validate.isNetworkAvailable(getApplicationContext()))
        return;
    setupActionTokenTracker();
    FacebookSdk.sdkInitialize(getApplicationContext());
    callbackManager=CallbackManager.Factory.create();


    loginBtn.setReadPermissions("public_profile");
    loginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
             profile = Profile.getCurrentProfile();
          txtResult.setText("Welcome"+profile.getName());
            if(profile.getName()!=null)
                btnToWeatherActivity.setVisibility(View.VISIBLE);
            else
                btnToWeatherActivity.setVisibility(View.GONE);

        }

        @Override
        public void onCancel() {
            btnToWeatherActivity.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Please check network connection",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException error) {
            btnToWeatherActivity.setVisibility(View.GONE);
            Log.d("FacebookSuccess",".."+ error.toString());
        }
    });

}

    @Override
    protected void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
    }

    public  void setupActionTokenTracker()
   {
       mTokenTracker=new AccessTokenTracker() {

           @Override
           protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
               Log.d("FB","Inside Tracker");
               if(currentAccessToken==null)
               {
                   btnToWeatherActivity.setVisibility(View.GONE);
               }
               else
               {  Validate.accessToken=currentAccessToken.toString();
                   btnToWeatherActivity.setVisibility(View.VISIBLE);
               }

           }
       };
   }

    @Override
    protected void onResume() {
        super.onResume();
       try{  if(!Validate.accessToken.equalsIgnoreCase(""))
               btnToWeatherActivity.setVisibility(View.VISIBLE);
       }catch (Exception e){
           btnToWeatherActivity.setVisibility(View.GONE);
       }

    }
}
