package es.moodbox.mooding.activities;

import android.os.Bundle;

import es.moodbox.mooding.R;
import es.moodbox.mooding.fragments.MoodsFragment;


public class MainActivity extends BasicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MoodsFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        trackScreen("home");
    }
}
