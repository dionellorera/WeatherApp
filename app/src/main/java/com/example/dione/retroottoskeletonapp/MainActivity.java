package com.example.dione.retroottoskeletonapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.dione.retroottoskeletonapp.api.models.Currently;
import com.example.dione.retroottoskeletonapp.api.models.Weather;
import com.example.dione.retroottoskeletonapp.application.ForecastApplication;
import com.example.dione.retroottoskeletonapp.event.GetWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEventError;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    ForecastApplication forecastApplication;
    TextView forecastTextView;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initialize();
//        sendWeatherRequest();
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng sydney = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    private void initialize(){
//        forecastTextView = (TextView) findViewById(R.id.forecastTextView);
    }

    private void sendWeatherRequest(){
        forecastApplication = new ForecastApplication();
        forecastApplication.mBus.post(new GetWeatherEvent(14.599512, 120.984222));
        forecastTextView.setText("Waiting for API Response");
    }

    @Subscribe
    public void onSendWeatherEvent(SendWeatherEvent sendWeatherEvent) {
        Weather weather = sendWeatherEvent.getWeather();
        Currently currently = weather.getCurrently();
        forecastTextView.setText(currently.getSummary());
    }

    @Subscribe
    public void onSendWeatherEventError(SendWeatherEventError sendWeatherEventError) {
        forecastTextView.setText(sendWeatherEventError.getmRetroFitError().toString());
    }
    @Override
    public void onResume() {
        super.onResume();
//        forecastApplication.mBus.register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
//        forecastApplication.mBus.unregister(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
//
//        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(14.5547, 121.0244);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
