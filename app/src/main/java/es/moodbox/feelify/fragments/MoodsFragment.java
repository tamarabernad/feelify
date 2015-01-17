package es.moodbox.feelify.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.moodbox.feelify.R;
import es.moodbox.feelify.activities.MoodCreationActivity;
import es.moodbox.feelify.giphy.model.GiphyModel;
import es.moodbox.feelify.giphy.services.GiphyServiceInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoodsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        List<String> moods = new ArrayList<>();
        moods.add("funny");
        moods.add("happy");
        moods.add("spicy");
        moods.add("sad");
        moods.add("grumpy");
        moods.add("muffin");
        moods.add("naughty");
        moods.add("laugh");



        final ArrayAdapter mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,moods);
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView =  (ListView)v.findViewById(R.id.moodlist_id);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://api.giphy.com/v1/gifs")
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();

                GiphyServiceInterface service = restAdapter.create(GiphyServiceInterface.class);
                String searchParameter = (String)mAdapter.getItem(position);
                service.search(searchParameter,new Callback<GiphyModel>() {
                    @Override
                    public void success(GiphyModel o, Response response) {
                        Log.d("MoodsFragment", o.toString());
                        Intent intent = new Intent(getActivity(), MoodCreationActivity.class);
                        String url = o.mGiphyData.get(0).images.image.mUrl;
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("MainActivity", error.getMessage());
                    }
                });
            }
        });

        listView.setAdapter(mAdapter);
        return v;
    }
}
