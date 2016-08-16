package com.example.dione.retroottoskeletonapp.event;

import com.example.dione.retroottoskeletonapp.api.models.Weather;

/**
 * Created by dione on 11/08/2016.
 */
public class SendWeatherEvent {
    private Weather mWeather;
    public SendWeatherEvent(Weather weather) {
        this.mWeather = weather;
    }
    public Weather getWeather() {
        return mWeather;
    }

}
