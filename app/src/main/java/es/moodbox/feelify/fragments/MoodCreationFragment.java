package es.moodbox.feelify.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.net.URL;

import es.moodbox.feelify.R;
import es.moodbox.feelify.services.FileDownloader;
import es.moodbox.feelify.utils.AppConstants;

public class MoodCreationFragment extends Fragment {

    private final static String TAG = MoodCreationFragment.class.getSimpleName();

    private WebView mWebView;
    private EditText mTextEdit;
    private String mUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_mood_creation, container, false);
        mWebView =(WebView) v.findViewById(R.id.webView);

        Button btShare =(Button) v.findViewById(R.id.btShare);
        mTextEdit = (EditText)v.findViewById(R.id.editText);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new DownloadFilesTask().execute(new URL(mUrl));
                } catch (Exception e) {
                    Log.e(TAG, "Ups...."+e.getMessage());
                }
            }
        });
        return v;
    }

    public class DownloadFilesTask extends AsyncTask<URL, Integer, File> {

        protected File doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            File tmp = null;
            for (int i = 0; i < count; i++) {
                tmp = FileDownloader.download(getActivity(), AppConstants.IMG_NAME, ".gif", urls[i]);
                if (tmp != null) {
                    totalSize += tmp.length();
                    Log.d(TAG, "Downloaded total: "+totalSize+ " url: "+urls[i].toString());
                }

                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return tmp;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(File result) {
            Uri path = Uri.fromFile(result);
	        Log.d(TAG, "Download done, sharing file: "+path);
	        try {
		        sendFileWithUri(result);
	        } catch (Exception e) {
		        Log.e(TAG, "ups: "+e.getMessage());
		        e.printStackTrace();
	        }
        }
    }

    private void sendFileWithUri(File image) throws Exception{
        String message = mTextEdit.getText() + "by feely";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("image/*");
        //Uri path = Uri.fromFile(new File("android.resource://com.android.mypackage/drawable/arrow.png"));
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        startActivity(sendIntent);
    }

    @Override
    public void onResume() {
        super.onResume();

        mUrl = getActivity().getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);
    }
}
