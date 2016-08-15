package com.example.dione.retroottoskeletonapp.event;

/**
 * Created by dione on 11/08/2016.
 */
public class GetWeatherEvent {
    private double mLatitude;
    private double mLongitude;
    public GetWeatherEvent(double latitude, double longitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }
    public double getLatitude() {
        return mLatitude;
    }
    public double getLongitude() {
        return mLongitude;
    }
}
