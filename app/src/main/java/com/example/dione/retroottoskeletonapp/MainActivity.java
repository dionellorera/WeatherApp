package com.example.dione.retroottoskeletonapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.example.dione.retroottoskeletonapp.api.interfaces.IApiResponse;
import com.example.dione.retroottoskeletonapp.application.ForecastApplication;
import com.example.dione.retroottoskeletonapp.event.GetWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEvent;
import com.example.dione.retroottoskeletonapp.event.SendWeatherEventError;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    ForecastApplication forecastApplication;
    GoogleMap mMap;
    IApiResponse iApiResponse;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        forecastApplication = new ForecastApplication();
        progressDialog = new ProgressDialog(this);
        initializeInterface();
        initializeMap();
        initializeAutoCompleteFragment();
    }

    private void initializeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initializeInterface() {
        iApiResponse = new IApiResponse() {
            @Override
            public void onMarkerAdded(LatLng latLng) {
                sendWeatherRequest(latLng.latitude, latLng.longitude);
            }

            @Override
            public void OnMarkerAdded(Place place) {
                sendWeatherRequest(place.getLatLng().latitude, place.getLatLng().longitude);
            }

            @Override
            public void onApiResponse(SendWeatherEvent sendWeatherEvent, Double latitude, Double longitude) {
                LatLng latLng = new LatLng(latitude, longitude);
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(sendWeatherEvent.getWeather().getCurrently().getSummary()));
                marker.showInfoWindow();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                progressDialog.dismiss();
            }
        };
    }

    private void initializeAutoCompleteFragment() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                iApiResponse.OnMarkerAdded(place);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void sendWeatherRequest(Double latitude, Double longitude) {
        forecastApplication.mBus.post(new GetWeatherEvent(latitude, longitude));
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Getting Weather Detail");
        progressDialog.show();


    }

    @Subscribe
    public void onSendWeatherEvent(SendWeatherEvent sendWeatherEvent) {
        iApiResponse.onApiResponse(sendWeatherEvent, sendWeatherEvent.getWeather().getLatitude(), sendWeatherEvent.getWeather().getLongitude());
    }

    @Subscribe
    public void onSendWeatherEventError(SendWeatherEventError sendWeatherEventError) {
        Toast.makeText(getApplicationContext(), sendWeatherEventError.getmRetroFitError().toString(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        forecastApplication.mBus.register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        forecastApplication.mBus.unregister(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    Location myLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (myLocation == null) {
                        Criteria criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_HIGH);
                        String provider = lm.getBestProvider(criteria, true);
                        myLocation = lm.getLastKnownLocation(provider);
                    }

                    if(myLocation!=null){
                        LatLng userLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14), 1500, null);
                    }
                }

                return false;
            }
        });


    }

    @Override
    public void onMapClick(LatLng latLng){
        iApiResponse.onMarkerAdded(latLng);
    }

}
