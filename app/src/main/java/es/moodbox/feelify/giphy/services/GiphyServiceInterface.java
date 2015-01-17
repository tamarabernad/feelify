package es.moodbox.feelify.giphy.services;

import es.moodbox.feelify.giphy.model.GiphyModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by machete on 1/11/15.
 */
public interface GiphyServiceInterface {

    @GET("/search?api_key=dc6zaTOxFJmzC")
    void search(@Query("q") String query, Callback<GiphyModel> cb);
}
