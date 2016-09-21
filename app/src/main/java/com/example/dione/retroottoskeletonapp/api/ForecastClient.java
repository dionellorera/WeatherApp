package com.example.dione.retroottoskeletonapp.api;

import com.example.dione.retroottoskeletonapp.api.interfaces.IWeather;
import com.example.dione.retroottoskeletonapp.api.models.Weather;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by dione on 11/08/2016.
 */
public class ForecastClient {
    private static final String BASE_URL = "https://api.darksky.net/forecast/";
    private static final String API_KEY = "e672a6f926d89cd9770047901f20847f";
    public static final String API_URL = BASE_URL + API_KEY;

    private static ForecastClient mForecastClient;
    private static RestAdapter mRestAdapter;

    public static ForecastClient getClient() {
        if (mForecastClient == null)
            mForecastClient = new ForecastClient();
        return mForecastClient;
    }

    private ForecastClient() {
        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

    public void getWeather(String latitude, String longitude, Callback<Weather> callback) {
        IWeather weather = mRestAdapter.create(IWeather.class);
        weather.getWeather(latitude, longitude, callback);
    }


}
