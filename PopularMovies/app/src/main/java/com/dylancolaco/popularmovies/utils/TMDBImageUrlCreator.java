package com.dylancolaco.popularmovies.utils;

/**
 * Created by Dylan on 16/07/17.
 */

public class TMDBImageUrlCreator {

    public static String create(String relativePath) {
        return "http://image.tmdb.org/t/p/w185/" + relativePath;
    }
}
