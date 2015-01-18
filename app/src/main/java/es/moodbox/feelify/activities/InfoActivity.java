package es.moodbox.feelify.activities;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import es.moodbox.feelify.R;

/**
 * Created by machete on 1/18/15.
 */
public class InfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        WebView webView = (WebView)findViewById(R.id.wvInfo);
        webView.loadUrl("file:///android_asset/info.html");
    }
}
