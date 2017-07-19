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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dylan on 18/07/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private static final String MOVIE = "movie";

    private Movie movie;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_release_date)
    TextView releaseDateView;

    @BindView(R.id.tv_user_rating)
    TextView userRatingView;

    @BindView(R.id.tv_synopsis)
    TextView synopsisView;

    @BindView(R.id.iv_poster)
    ImageView posterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movie = JsonSerializer.deserialize(getIntent().getStringExtra(MOVIE), Movie.class);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.detail_activity_name);
        }

        load();
    }

    private void load() {
        titleView.setText(movie.getTitle());
        releaseDateView.setText(movie.getRelease_date());
        userRatingView.setText(String.format("%.2f", movie.getVote_average()));
        synopsisView.setText(movie.getOverview());

        String imageUrl = TMDBImageUrlCreator.create(movie.getPoster_path());
        Picasso.with(this).load(imageUrl).into(posterView);
    }

    public static Intent createIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MOVIE, JsonSerializer.serialize(movie));
        return intent;
    }
}
