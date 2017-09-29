package com.example.android.quakereport;

/**
 * Created by shalom on 2017-04-12.
 */

public class EarthquakeData {

    private double magnitude;
    private String place;
    private long unixTime;
    private String url;

    public EarthquakeData(double magnitude, String place, long unixTime, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.unixTime = unixTime;
        this.url = url;
    }

    public double getMagnitude() { return magnitude; }

    public String getPlace() {
        return place;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public String getUrl() { return url; }
}
