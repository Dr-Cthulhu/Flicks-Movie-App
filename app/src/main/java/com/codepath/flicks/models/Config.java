package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mpan on 6/22/17.
 */

public class Config {


    String imageBaseUrl;
    String posterSize;
    String backdropSize;

    public Config(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");

        imageBaseUrl = images.getString("secure_base_url");
        JSONArray posterSizes = images.getJSONArray("poster_sizes");
        posterSize = posterSizes.optString(3, "w342");
        JSONArray bdSizes = images.getJSONArray("backdrop_sizes");
        backdropSize = bdSizes.optString(1, "w780");

    }

    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
