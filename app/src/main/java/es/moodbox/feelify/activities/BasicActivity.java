package es.moodbox.feelify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import es.moodbox.feelify.interfaces.IndeterminateStateInterface;

/**
 * Created by victoriza on 17/01/15.
 */
public class BasicActivity extends Activity implements IndeterminateStateInterface {

    public void onCreate(Bundle savedState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedState);
    }

    public void showActionBarSpinner() {
        this.setProgressBarIndeterminate(true);
    }

    public void hideActionBarSpinner() {
        this.setProgressBarIndeterminate(false);
    }
}
