package com.example.dione.retroottoskeletonapp.manager;

import android.content.Context;
import android.util.Log;

import com.example.dione.retroottoskeletonapp.api.ForecastClient;
import com.example.dione.retroottoskeletonapp.api.models.Weather;
import com.example.dione.retroottoskeletonapp.event.GetWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEventError;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by dione on 11/08/2016.
 */
public class ForecastManager {
    private Context mContext;
    private Bus mBus;
    private ForecastClient sForecastClient;
    public ForecastManager(Context context, Bus bus) {
        this.mContext = context;
        this.mBus = bus;
        sForecastClient = ForecastClient.getClient();
    }

    @Subscribe
    public void onGetWeatherEvent(GetWeatherEvent getWeatherEvent) {
        String latitude = Double.toString(getWeatherEvent.getLatitude()).trim();
        String longitude = Double.toString(getWeatherEvent.getLongitude()).trim();
        Callback<Weather> callback = new Callback<Weather>() {
            @Override
            public void success(Weather weather, retrofit.client.Response response) {
                mBus.post(new SendWeatherEvent(weather));
            }

            @Override
            public void failure(RetrofitError error) {
                mBus.post(new SendWeatherEventError(error));
            }
        };
        sForecastClient.getWeather(latitude, longitude, callback);
    }
}
