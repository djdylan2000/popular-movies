package com.dylancolaco.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dylancolaco.popularmovies.client.TMDBHttpClient;
import com.dylancolaco.popularmovies.models.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MenuItem sortOrderItem;
    TMDBHttpClient.SortType currentSortType = TMDBHttpClient.SortType.RATING;

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        recyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);
        load(TMDBHttpClient.SortType.RATING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        sortOrderItem = menu.findItem(R.id.sort_order);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_order:
                if (item.getTitle().equals(getString(R.string.sort_popularity))) {
                    load(TMDBHttpClient.SortType.POPULARITY);
                    return true;
                }
                load(TMDBHttpClient.SortType.RATING);
                return true;
            case R.id.refresh:
                load(currentSortType);
        }
        return super.onOptionsItemSelected(item);
    }

    private void load(final TMDBHttpClient.SortType sortType) {
        new AsyncTask<Void, Void, List<Movie>>() {
            @Override
            protected List<Movie> doInBackground(Void... voids) {
                try {
                    return Providers.getTMDBClient(MoviesActivity.this).getMovies(sortType);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                    return new ArrayList<>();
                }
            }

            @Override
            protected void onPostExecute(List<Movie> movies) {
                if (movies == null || movies.isEmpty()) {
                    Toast.makeText(MoviesActivity.this, R.string.network_issue, Toast.LENGTH_LONG).show();
                    return;
                }
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, 2);
                MoviesAdapter moviesAdapter = new MoviesAdapter(movies, MoviesActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(moviesAdapter);
                sortOrderItem.setTitle(getNextSortMethod(sortType));
                currentSortType = sortType;
            }
        }.execute();
    }

    private int getNextSortMethod(TMDBHttpClient.SortType sortType) {
        return sortType == TMDBHttpClient.SortType.POPULARITY ? R.string.sort_rating : R.string.sort_popularity;
    }
}
