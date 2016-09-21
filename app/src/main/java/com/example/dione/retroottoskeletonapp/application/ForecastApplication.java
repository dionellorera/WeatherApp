package com.example.dione.retroottoskeletonapp.application;

import android.app.Application;

import com.example.dione.retroottoskeletonapp.bus.BusProvider;
import com.example.dione.retroottoskeletonapp.manager.ForecastManager;
import com.squareup.otto.Bus;

/**
 * Created by dione on 11/08/2016.
 */
public class ForecastApplication extends Application {
    private ForecastManager mForecastManager;
    public final Bus mBus = BusProvider.getInstance();
    @Override
    public void onCreate() {
        super.onCreate();
        mForecastManager = new ForecastManager(this, mBus);
        mBus.register(mForecastManager);
        mBus.register(this);
    }
}
