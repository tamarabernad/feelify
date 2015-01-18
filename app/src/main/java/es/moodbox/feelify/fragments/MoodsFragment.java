package es.moodbox.feelify.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.moodbox.feelify.R;
import es.moodbox.feelify.activities.InfoActivity;
import es.moodbox.feelify.activities.MoodCreationActivity;
import es.moodbox.feelify.adapters.MoodsAdapter;
import es.moodbox.feelify.giphy.model.MoodModel;

public class MoodsFragment extends Fragment {

	List<MoodModel> moodModels;

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

		List<String> moodsTags = new ArrayList<>();
		moodsTags.add("funny,laugh,lol");
		moodsTags.add("happy,laugh,jump");
		moodsTags.add("spicy");
		moodsTags.add("sad,tears");
		moodsTags.add("grumpy");
		moodsTags.add("muffin");
		moodsTags.add("naughty");
		moodsTags.add("laugh");

		moodModels = new ArrayList<>();
		MoodModel mood;
		int index = 0;
		for (String moodName : moods) {
			mood = new MoodModel();
			mood.id = index + "";
			mood.name = moodName;
			mood.searchTags = moodsTags.get(index);
			moodModels.add(mood);
		}


		final MoodsAdapter mAdapter = new MoodsAdapter(getActivity(), R.layout.moods_list_item, moodModels);
		View v = inflater.inflate(R.layout.fragment_main, container, false);

		ListView listView = (ListView) v.findViewById(R.id.moodlist_id);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("MoodsFragment", "position: " + moodModels.get(position));
				Intent intent = new Intent(getActivity(), MoodCreationActivity.class);
				intent.putExtra("model", moodModels.get(position));
				startActivity(intent);
			}
		});

		listView.setAdapter(mAdapter);


        ImageButton btInfo = (ImageButton)v.findViewById(R.id.btInfo);
        btInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent);
            }
        });
		return v;
	}
}
