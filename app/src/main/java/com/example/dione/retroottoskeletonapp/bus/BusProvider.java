package com.example.dione.retroottoskeletonapp.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by dione on 11/08/2016.
 */
public class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);
    public static Bus getInstance() {
        return BUS;
    }
    private BusProvider() {
    }
}
