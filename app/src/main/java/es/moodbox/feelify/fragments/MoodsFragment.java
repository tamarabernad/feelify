package es.moodbox.feelify.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.moodbox.feelify.R;
import es.moodbox.feelify.activities.MoodCreationActivity;
import es.moodbox.feelify.adapters.MoodsAdapter;
import es.moodbox.feelify.giphy.model.SimpleModel;
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



        final MoodsAdapter mAdapter = new MoodsAdapter(getActivity(),R.layout.moods_list_item, moods);
        View v =  inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView =  (ListView)v.findViewById(R.id.moodlist_id);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setEndpoint("http://api.giphy.com/v1/stickers/")
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build();

                GiphyServiceInterface service = restAdapter.create(GiphyServiceInterface.class);
                String searchParameter = (String)mAdapter.getItem(position);
                service.random(searchParameter, new Callback<SimpleModel>() {
                    @Override
                    public void success(SimpleModel o, Response response) {
                        Log.d("MoodsFragment", o.toString());
                        Intent intent = new Intent(getActivity(), MoodCreationActivity.class);
                        String url = o.mGiphyData.mUrl;
                        intent.putExtra("url", url);
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
