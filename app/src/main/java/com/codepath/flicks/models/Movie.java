package com.codepath.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mpan on 6/22/17.
 */

@Parcel
public class Movie {

    int id;
    String title;
    String synopsis;
    String posterPath;
    String backdropPath;
    double voteAverage;
    int voteNumber;

    public Movie() {}

    public Movie(JSONObject object) throws JSONException {
        id = object.getInt("id");
        title = object.getString("title");
        synopsis = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString(("backdrop_path"));
        voteAverage = object.getDouble("vote_average");
        voteNumber = object.getInt("vote_count");
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteNumber() {
        return voteNumber;
    }

    public int getId() {
        return id;
    }

}
