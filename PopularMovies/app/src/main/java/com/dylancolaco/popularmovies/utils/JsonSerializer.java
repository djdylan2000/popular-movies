package com.dylancolaco.popularmovies.utils;

import com.google.gson.Gson;

/**
 * Created by Dylan on 16/07/17.
 */

public class JsonSerializer {

    private static Gson gson = new Gson();

    public static <T> T deserialize(String objectString, Class<T> objectClass) {
         return (T) gson.fromJson(objectString, objectClass);
    }

    public static String serialize(Object object) {
        return gson.toJson(object);
    }
}
