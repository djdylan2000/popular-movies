package com.dylancolaco.popularmovies;

import android.content.Context;

import com.dylancolaco.popularmovies.client.TMDBHttpClient;
import com.dylancolaco.popularmovies.client.TMDBHttpClientImpl;

/**
 * Created by Dylan on 16/07/17.
 */

public class Providers {

    public static TMDBHttpClient getTMDBClient(Context context) {
        return new TMDBHttpClientImpl();
    }
}
