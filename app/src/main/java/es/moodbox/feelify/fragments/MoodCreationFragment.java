package es.moodbox.feelify.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import es.moodbox.feelify.R;
import es.moodbox.feelify.utils.AppConstants;
import es.moodbox.feelify.utils.DLManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MoodCreationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MoodCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoodCreationFragment extends Fragment {
    private WebView mWebView;
    private EditText mTextEdit;
    private String mUrl;
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {


                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    Cursor c = DLManager.manger.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {

                            String uriString = c
                                    .getString(c
                                            .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            Log.d("MoodCreationFragment",uriString);

                            //File mFile = new File(uriString);
                            sendFileWithUri(uriString);

                        }
                    }
                }
            }
        };

        getActivity().registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_mood_creation, container, false);
        mView = v;
        mWebView =(WebView) v.findViewById(R.id.webView);
        Button btShare =(Button) v.findViewById(R.id.btShare);
        mTextEdit = (EditText)v.findViewById(R.id.editText);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DLManager.useDownloadManager(mUrl, AppConstants.IMG_DIR, AppConstants.IMG_NAME+".gif", getActivity());
                /*try {
                    downloadImageWithUrl(mUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });
        return v;
    }
    private void sendFileWithUri(String imageUrl){
        String message = mTextEdit.getText() + "by feely";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("image/*");
        //Uri path = Uri.fromFile(new File("android.resource://com.android.mypackage/drawable/arrow.png"));
        sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(imageUrl));
        startActivity(sendIntent);
    }
    @Override
    public void onResume() {
        super.onResume();
        mUrl = getActivity().getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);
    }
}
