package com.dylancolaco.popularmovies.client;

import com.dylancolaco.popularmovies.models.Movie;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dylan on 16/07/17.
 */

public interface TMDBHttpClient {

    public enum SortType {
        RATING, POPULARITY
    }

    List<Movie> getMovies(SortType sortType) throws IOException;
}
