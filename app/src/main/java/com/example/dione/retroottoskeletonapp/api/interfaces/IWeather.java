package com.example.dione.retroottoskeletonapp.api.interfaces;

import com.example.dione.retroottoskeletonapp.api.models.Weather;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by dione on 11/08/2016.
 */
public interface IWeather {
    @GET("/{latitude},{longitude}")
    void getWeather(@Path("latitude") String latitude, @Path("longitude") String longitude, Callback<Weather> callback);
}
