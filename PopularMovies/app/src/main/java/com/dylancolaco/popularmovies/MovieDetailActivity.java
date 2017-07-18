package com.dylancolaco.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dylancolaco.popularmovies.models.Movie;
import com.dylancolaco.popularmovies.utils.JsonSerializer;
import com.dylancolaco.popularmovies.utils.TMDBImageUrlCreator;
import com.squareup.picasso.Picasso;

/**
 * Created by Dylan on 18/07/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE = "movie";

    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = JsonSerializer.deserialize(getIntent().getStringExtra(MOVIE), Movie.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.detail_activity_name);
        }
        load();
    }

    private void load() {
        ((TextView) findViewById(R.id.tv_title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.tv_release_date)).setText(movie.getRelease_date());
        ((TextView) findViewById(R.id.tv_user_rating)).setText(String.format("%.2f", movie.getVote_average()));
        ((TextView) findViewById(R.id.tv_synopsis)).setText(movie.getOverview());

        String imageUrl = TMDBImageUrlCreator.create(movie.getPoster_path());
        Picasso.with(this).load(imageUrl).into((ImageView) findViewById(R.id.iv_poster));
    }

    public static Intent createIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE, JsonSerializer.serialize(movie));
        return intent;
    }
}
