package com.example.weatherinfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherinfo.DataModel.Model;
import com.example.weatherinfo.DataModel.Weather;
import com.example.weatherinfo.DataModel.WeatherAPI;

import com.example.weatherinfo.R;
import com.example.weatherinfo.validate.ApiClient;
import com.example.weatherinfo.validate.Validate;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by server on 8/26/2016.
 */
public class WeatherActivity  extends Activity{
    ProgressDialog pd;
    @InjectView(R.id.txtCityName) EditText txtCityname;
    @InjectView(R.id.weatherBtn)Button getbtnWeather;
    @InjectViews({R.id.txtTemperature,R.id.txtWeatherDescription,R.id.txtPressure,R.id.txtHumidity})List<TextView> txtWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);
       pd=new ProgressDialog(this);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.weatherBtn)
    void getWeatherFromCity()
    {
        try{
           if(! Validate.isNetworkAvailable(this))
           {  Toast.makeText(getApplicationContext(),"Please check your network connectivity",Toast.LENGTH_LONG).show();
               return;}
            else if (txtCityname.getText().toString().equalsIgnoreCase("")||txtCityname.getText().toString().equalsIgnoreCase(" "))
           { Toast.makeText(getApplicationContext(),"Please enter city name",Toast.LENGTH_LONG).show();return;}
        pd.setTitle("Loading...");
        pd.show();

    }catch (Exception e)
    {
        e.printStackTrace();
    }
        WeatherAPI weatherAPI= ApiClient.getClient().create(WeatherAPI.class);
        Call<Model> call=weatherAPI.getWeatherDetails(txtCityname.getText().toString(),"96ba9086a5da655a0dd1507ddbeecd03");
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Model  wether=response.body();

                if(wether!=null)
                {
                    getReportForCity(response.body());
                    txtCityname.setText("");
                }
                else {
                    if(pd.isShowing())
                    {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Unable to retrieve weather infor",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                    try{
                        if(pd.isShowing())
                            pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Unable to retrieve wheather data for the city",Toast.LENGTH_LONG).show();
                        txtCityname.setText("");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            }
        });
    }
    public void getReportForCity(Model model)
    {
        try{if(pd.isShowing())
            pd.dismiss();
        double temperature=model.getMain().getTemp()-273.15;
        String txtTemperature=String.format("%.2f",temperature)+"Degrees";
        txtWeather.get(0).setText(txtTemperature);
        txtWeather.get(2).setText(model.getMain().getPressure().toString());
        txtWeather.get(3).setText(model.getMain().getHumidity().toString());
        txtWeather.get(1).setText(model.getWeather().get(0).getDescription().toString());
        ((TextView)findViewById(R.id.txtCity_name)).setText(txtCityname.getText());}
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK)
        {try{
            startActivity(new Intent(WeatherActivity.this,MainActivity.class));
            finish();
        }
        catch (Exception e)
        {

        }
        }
        return super.onKeyDown(keyCode, event);
    }
}
