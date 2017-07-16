package com.dylancolaco.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dylancolaco.popularmovies.client.TMDBHttpClient;
import com.dylancolaco.popularmovies.models.Movie;
import com.dylancolaco.popularmovies.utils.JsonSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, List<Movie>>() {
                    @Override
                    protected List<Movie> doInBackground(Void... voids) {
                        try {
                            return Providers.getTMDBClient(MoviesActivity.this).getMovies(TMDBHttpClient.SortType.RATING);
                        } catch (IOException e) {
                            Log.e("IOException", e.getMessage());
                            return new ArrayList<>();
                        }
                    }

                    @Override
                    protected void onPostExecute(List<Movie> movies) {
                        MoviesActivity.this.movies = movies;
                        Log.v("TEST-MoviesAct", JsonSerializer.serialize(movies));
                    }
                }.execute();
            }
        });
    }
}
