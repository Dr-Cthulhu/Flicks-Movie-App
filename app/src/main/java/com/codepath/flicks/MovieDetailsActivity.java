package com.codepath.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.flicks.MovieListActivity.API_BASE_URL;
import static com.codepath.flicks.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    public final static String TAG = "MovieDetailsActivity";

    Movie movie;
    AsyncHttpClient client;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvSynopsis) TextView tvSynopsis;
    @BindView(R.id.tvVoteNumber) TextView tvVoteNumber;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.tvGenres) TextView genres;
    @BindView(R.id.tvReleaseDate) TextView releaseDate;
    @BindView(R.id.tvTagline) TextView tagLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        client = new AsyncHttpClient();
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("Movies Detail Activity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvSynopsis.setText(movie.getSynopsis());
        tvVoteNumber.setText(String.format("(%d votes)", movie.getVoteNumber()));

        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        getMovieDetails();
    }

    private void getMovieDetails() {
        String url = API_BASE_URL + "/movie/" + Integer.toString(movie.getId());
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        Log.i(TAG, url);
        Log.i(TAG, API_KEY_PARAM);
        Log.i(TAG, getString(R.string.api_key));
        Log.i(TAG, String.valueOf(params == null || url == null));
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String release = response.getString("release_date");
                    Toast.makeText(getApplicationContext(), release, Toast.LENGTH_LONG).show();
                    String tag = response.getString("tagline");

                    String genreList = "";
                    JSONArray genre = response.getJSONArray("genres");
                    for (int i = 0; i < genre.length() - 1; i++) {
                        genreList += genre.getJSONObject(i).getString("name") + ", ";
                    }
                    genreList += genre.getJSONObject(genre.length() - 1).getString("name");

                    tagLine.setText(tag);
                    releaseDate.setText("Date of release: " + release);
                    genres.setText("Genres: " + genreList);

                    Log.i(TAG, String.format("Loaded details for &s", movie.getTitle()));
                } catch (JSONException e) {
                    logError("Failed to parse movie details", e, true);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Could not retrieve movie details", throwable, true);
            }
        });
    }

    private void logError(String message, Throwable error, boolean alertUser) {
        Log.e(TAG, message, error);
        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
