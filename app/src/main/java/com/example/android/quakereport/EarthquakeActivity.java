/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<EarthquakeData>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10";
    private EarthquakeDataAdapter adapter;
    private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_list_activity);

        if (!checkNetworkConnection()) {
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.INVISIBLE);
            TextView noNetworkConnection = (TextView) findViewById(R.id.no_network_connection);
            noNetworkConnection.setVisibility(View.VISIBLE);
        } else {
            Log.v(LOG_TAG, "initLoader() is about to get called");
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        }
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<EarthquakeData>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "onCreateLoader()");
        return new EarthquakeAsyncTaskLoader(EarthquakeActivity.this, USGS_URL);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<EarthquakeData>> loader, ArrayList<EarthquakeData> data) {
        Log.v(LOG_TAG, "onLoadFinished()");

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        adapter = new EarthquakeDataAdapter(getApplicationContext(), data);
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setEmptyView(findViewById(android.R.id.empty));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<EarthquakeData>> loader) {
        Log.v(LOG_TAG, "onLoaderReset()");
        adapter.clear();
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
