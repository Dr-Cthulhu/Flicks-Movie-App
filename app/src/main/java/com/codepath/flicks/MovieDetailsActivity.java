package com.codepath.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicks.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    TextView tvTitle;
    TextView tvSynopsis;
    TextView tvVoteNumber;
    RatingBar rbVoteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvVoteNumber = (TextView) findViewById(R.id.tvVoteNumber);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("Movies Detail Activity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvVoteNumber.setText(String.format("(%d votes)", movie.getVoteNumber()));

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
    }
}
