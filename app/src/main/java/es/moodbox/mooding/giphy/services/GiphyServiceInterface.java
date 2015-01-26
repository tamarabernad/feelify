package es.moodbox.mooding.giphy.services;

import es.moodbox.mooding.giphy.model.GiphyModel;
import es.moodbox.mooding.giphy.model.SimpleModel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by machete on 1/11/15.
 */
public interface GiphyServiceInterface {

    @GET("/search?limit=1&api_key=TlK63EDDEoewNzx3akE")
    void searchRandom(@Query("q") String query, @Query("offset") int offset, Callback<GiphyModel> cb);

    @GET("/search?limit=1&api_key=TlK63EDDEoewNzx3akE")
    void search(@Query("q") String query, Callback<GiphyModel> cb);

    @GET("/random?api_key=TlK63EDDEoewNzx3akE")
    void random(@Query("q") String query, Callback<SimpleModel> cb);
}
