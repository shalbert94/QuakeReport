package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by shalom on 2017-04-20.
 */

public class EarthquakeAsyncTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<EarthquakeData>> {
    public static final String LOG_TAG = EarthquakeAsyncTaskLoader.class.getName();
    String urlAsString;

    public EarthquakeAsyncTaskLoader(Context context, String urlAsString) {
        super(context);
        this.urlAsString = urlAsString;
    }

    @Override
    protected void onStartLoading() {
        Log.v(LOG_TAG, "onStartLoading()");
        super.onStartLoading();
    }

    @Override
    public ArrayList<EarthquakeData> loadInBackground() {
        Log.v(LOG_TAG, "loadInBackground()");
        String urlAsString = getUrlAsString();

        if (urlAsString.length() < 1 || urlAsString == null) {
            return null;
        }

        ArrayList<EarthquakeData> earthquakes = QueryUtils.fetchEarthquakeData(urlAsString);
        return earthquakes;
    }

    private String getUrlAsString() {
        return urlAsString;
    }
}
