package com.example.weatherinfo.DataModel;

/**
 * Created by server on 8/26/2016.
 */


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAPI {
///data/2.5/weather?q=London&APPID=96ba9086a5da655a0dd1507ddbeecd03   /data/2.5/weather?q={city}&APPID={API_Key}
    @GET("/data/2.5/weather?")
    Call<Model> getWeatherDetails(@Query("q") String city,@Query("APPID") String apiKey);
}
