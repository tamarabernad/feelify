package es.moodbox.feelify.activities;

import android.os.Bundle;

import es.moodbox.feelify.R;
import es.moodbox.feelify.fragments.MoodCreationFragment;

/**
 * Created by machete on 1/11/15.
 */
public class MoodCreationActivity extends BasicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    setTitle("");
        setContentView(R.layout.activity_mood_creation);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MoodCreationFragment())
                    .commit();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        trackScreen("mood-creation");
    }

}
