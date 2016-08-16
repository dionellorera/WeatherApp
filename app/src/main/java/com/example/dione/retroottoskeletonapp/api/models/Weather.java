package com.example.dione.retroottoskeletonapp.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dione on 11/08/2016.
 */
public class Weather {
    @SerializedName("currently")
    private Currently mCurrently;
    public Currently getCurrently() {
        return mCurrently;
    }

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
