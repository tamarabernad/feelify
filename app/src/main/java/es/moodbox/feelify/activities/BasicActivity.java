package es.moodbox.feelify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import es.moodbox.feelify.application.FeelifyApplication;
import es.moodbox.feelify.interfaces.IndeterminateStateInterface;

/**
 * Created by victoriza on 17/01/15.
 */
public class BasicActivity extends Activity implements IndeterminateStateInterface {

    public void onCreate(Bundle savedState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedState);
    }

    public void trackEvent(String key, String eventName) {
        Tracker t = getTracker();

        t.set(key,eventName);

        t.send(new HitBuilders.AppViewBuilder().build());
    }

    protected void trackScreen(String screenName) {
        // Get tracker.
        Tracker t = getTracker();

        // Set screen name.
        // Where path is a String representing the screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    private Tracker getTracker() {
        // Get tracker.
        Tracker t = ((FeelifyApplication) getApplication()).getTracker(FeelifyApplication.TrackerName.APP_TRACKER);
        t.setAppVersion(((FeelifyApplication) getApplication()).getAppVersion());
        return t;
    }

    public void showActionBarSpinner() {
        this.setProgressBarIndeterminate(true);
    }

    public void hideActionBarSpinner() {
        this.setProgressBarIndeterminate(false);
    }
}
