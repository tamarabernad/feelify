package es.moodbox.feelify.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import es.moodbox.feelify.R;
import es.moodbox.feelify.fragments.MoodsFragment;
import es.moodbox.feelify.giphy.model.GiphyModel;
import es.moodbox.feelify.giphy.services.GiphyServiceInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MoodsFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.giphy.com/v1/gifs")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        GiphyServiceInterface service = restAdapter.create(GiphyServiceInterface.class);

        service.search("funny",new Callback<GiphyModel>() {
            @Override
            public void success(GiphyModel o, Response response) {
                Log.d("MainActivity", o.toString());

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MainActivity", error.getMessage());
            }
        });*/
    }
}
