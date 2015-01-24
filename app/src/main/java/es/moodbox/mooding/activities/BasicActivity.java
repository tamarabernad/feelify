package es.moodbox.mooding.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import es.moodbox.mooding.application.FeelifyApplication;
import es.moodbox.mooding.interfaces.IndeterminateStateInterface;

/**
 * Created by victoriza on 17/01/15.
 */
public class BasicActivity extends Activity implements IndeterminateStateInterface {

    private final static String TAG = BasicActivity.class.getSimpleName();

    public void onCreate(Bundle savedState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedState);
    }

    public void trackEvent(String categoryId, String action) {
        Log.d(TAG, " track event categoryId:  "+categoryId+ " action: "+action);
        Tracker t = getTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory(categoryId)
                .setAction(action)
                .build());
    }

    protected void trackScreen(String screenName) {
        Log.d(TAG, " track event screen:  "+screenName);
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
        // Enable Display Features.
        t.enableAdvertisingIdCollection(true);
        return t;
    }

    public void showActionBarSpinner() {
        this.setProgressBarIndeterminate(true);
    }

    public void hideActionBarSpinner() {
        this.setProgressBarIndeterminate(false);
    }
}
