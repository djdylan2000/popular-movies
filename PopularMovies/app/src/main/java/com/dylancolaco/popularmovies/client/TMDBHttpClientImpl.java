package com.dylancolaco.popularmovies.client;

import android.net.Uri;
import android.util.Log;

import com.dylancolaco.popularmovies.models.Movie;
import com.dylancolaco.popularmovies.models.MoviesResponse;
import com.dylancolaco.popularmovies.utils.JsonSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Dylan on 16/07/17.
 */

public class TMDBHttpClientImpl implements TMDBHttpClient {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    //TODO: insert API Key
    private static final String API_KEY = "";

    @Override
    public List<Movie> getMovies(SortType sortType) throws IOException {
        URL requestUrl = createRequestUrl(sortType);
        String responseString = getResponseFromHttpUrl(requestUrl);
        MoviesResponse response = JsonSerializer.deserialize(responseString, MoviesResponse.class);
        return response.getResults();
    }

    private URL createRequestUrl(SortType sortType) {
        String sortPathParameter = getSortPath(sortType);
        Uri requestUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(sortPathParameter)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        try {
            return new URL(requestUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TMDBHttpClientImpl.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    private String getSortPath(SortType sortType) {
        switch (sortType) {
            case RATING:
                return "top_rated";
            case POPULARITY:
                return "popular";
        }
        throw new IllegalArgumentException("invalid sort type: " + sortType);
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
