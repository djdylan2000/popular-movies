package com.dylancolaco.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dylancolaco.popularmovies.models.Movie;
import com.dylancolaco.popularmovies.utils.TMDBImageUrlCreator;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Dylan on 16/07/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String imageUrl = TMDBImageUrlCreator.create(movie.getPoster_path());
        holder.setImage(context, imageUrl);
        setClickListener(holder.getImageView(), position);
    }

    private void setClickListener(View view, int position) {
        final Movie selectedMovie = movies.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movieDetailActivityIntent = MovieDetailActivity.createIntent(context, selectedMovie);
                movieDetailActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(movieDetailActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
        }

        void setImage(Context context, String imageUrl) {
            Picasso.with(context).load(imageUrl).into(imageView);
        }

        ImageView getImageView() {
            return imageView;
        }

    }
}
