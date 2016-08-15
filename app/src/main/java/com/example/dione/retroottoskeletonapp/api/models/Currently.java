package com.example.dione.retroottoskeletonapp.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dione on 11/08/2016.
 */
public class Currently {
    @SerializedName("time")
    private long mTime;
    public long getTime() {
        return mTime;
    }
    @SerializedName("summary")
    private String mSummary;
    public String getSummary() {
        return mSummary;
    }
}
