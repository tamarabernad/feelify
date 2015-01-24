package es.moodbox.mooding.fragments;

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

import es.moodbox.mooding.R;
import es.moodbox.mooding.activities.BasicActivity;
import es.moodbox.mooding.activities.InfoActivity;
import es.moodbox.mooding.activities.MoodCreationActivity;
import es.moodbox.mooding.adapters.MoodsAdapter;
import es.moodbox.mooding.giphy.model.MoodModel;

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
        moods.add("drunk");
		moods.add("muffin");
		moods.add("naughty");
		moods.add("laugh");
        moods.add("party");

		List<String> moodsTags = new ArrayList<>();
		moodsTags.add("funny");
		moodsTags.add("happy");
		moodsTags.add("spicy");
		moodsTags.add("sad");
		moodsTags.add("grumpy");
        moodsTags.add("drunk");
		moodsTags.add("muffin");
		moodsTags.add("naughty");
		moodsTags.add("laugh");
        moodsTags.add("party");

		moodModels = new ArrayList<>();
		MoodModel mood;
		int index = 0;
		for (String moodName : moods) {
			mood = new MoodModel();
			mood.id = moodName.toLowerCase()+"_"+index;
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

                ((BasicActivity)getActivity()).trackEvent("click","mood-select/"+moodModels.get(position).id);

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
