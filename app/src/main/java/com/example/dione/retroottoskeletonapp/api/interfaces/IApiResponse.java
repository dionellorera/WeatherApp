package com.example.dione.retroottoskeletonapp.api.interfaces;

import com.example.dione.retroottoskeletonapp.event.SendWeatherEvent;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by dione on 16/08/2016.
 */
public interface IApiResponse  {
    void onMarkerAdded(LatLng latLng);
    void OnMarkerAdded(Place place);
    void onApiResponse(SendWeatherEvent sendWeatherEvent, Double latitude, Double longitude);
}
