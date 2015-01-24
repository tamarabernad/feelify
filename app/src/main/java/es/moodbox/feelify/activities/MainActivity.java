package es.moodbox.feelify.activities;

import android.os.Bundle;

import es.moodbox.feelify.R;
import es.moodbox.feelify.fragments.MoodsFragment;


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
}
