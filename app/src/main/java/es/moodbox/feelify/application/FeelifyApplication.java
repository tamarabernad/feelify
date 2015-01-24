package es.moodbox.feelify.application;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import es.moodbox.feelify.R;

/**
 * Created by victoriza on 24/01/15.
 */
public class FeelifyApplication extends Application {

	private final static String TAG = FeelifyApplication.class.getSimpleName();

	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 *
	 * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
	 * storing them all in Application object helps ensure that they are created only once per
	 * application instance.
	 */
	public enum TrackerName {
		APP_TRACKER, // Tracker used only in this app.
	}

	private static HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public synchronized Tracker getTracker(TrackerName trackerId) {

		Log.d(TAG, " Google Analytics tracker cfg: "+trackerId);
		if (!mTrackers.containsKey(trackerId)) {

			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = analytics.newTracker(R.xml.google_analytics);;
			mTrackers.put(trackerId, t);

		}
		return mTrackers.get(trackerId);
	}
}
