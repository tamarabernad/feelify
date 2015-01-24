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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.net.URL;
import java.util.Random;

import es.moodbox.feelify.R;
import es.moodbox.feelify.activities.BasicActivity;
import es.moodbox.feelify.giphy.model.GiphyModel;
import es.moodbox.feelify.giphy.model.MoodModel;
import es.moodbox.feelify.giphy.services.GiphyServiceInterface;
import es.moodbox.feelify.services.FileDownloader;
import es.moodbox.feelify.utils.AppConstants;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoodCreationFragment extends Fragment {

    private final static String TAG = MoodCreationFragment.class.getSimpleName();

    private WebView mWebView;
    private EditText mTextEdit;
    private MoodModel mMoodModel;
    private GiphyModel mSimpleModel;
    private View mLoading;
    private RestAdapter restAdapter;
    private GiphyServiceInterface service;

    private ImageButton btShare;
    private ImageButton mNext;

    private Animation animScale;
    private Animation animRotate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mood_creation, container, false);
        mWebView = (WebView) v.findViewById(R.id.webView);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        mLoading = v.findViewById(R.id.loading_view);
        mLoading.setVisibility(View.GONE);

        mTextEdit = (EditText) v.findViewById(R.id.editText);

        animScale= AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        animRotate= AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

        mMoodModel = (MoodModel) getActivity().getIntent().getSerializableExtra("model");
        mTextEdit.setHint("mooding "+mMoodModel.name);
        mTextEdit.setSelection(0,mTextEdit.getText().length());

        btShare = (ImageButton) v.findViewById(R.id.btShare);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BasicActivity)getActivity()).trackEvent("click","mood-creation/share");
                try {
                    if (mSimpleModel == null 
                            || mSimpleModel.mGiphyData == null
                            ||mSimpleModel.mGiphyData.isEmpty()
                            || mSimpleModel.mGiphyData.get(0).images == null
                            || mSimpleModel.mGiphyData.get(0).images.image == null
                            || mSimpleModel.mGiphyData.get(0).images.image.mUrl == null ) {
                        somethingWentWrong();
                    }else {
                        new DownloadFilesTask().execute(new URL(mSimpleModel.mGiphyData.get(0).images.image.mUrl));
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Ups...." + e.getMessage());
                    somethingWentWrong();
                }
            }
        });

        mNext = (ImageButton) v.findViewById(R.id.btNext);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BasicActivity)getActivity()).trackEvent("click","mood-creation/random");
                mWebView.loadUrl("about:blank");
                mNext.startAnimation(animRotate);
                load();
            }
        });
        return v;
    }

    private void somethingWentWrong() {
        Toast toast = Toast.makeText(getActivity(),"You just broke the app :_(",Toast.LENGTH_LONG);
        toast.show();
    }

    public void load() {
        showLoading();
        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.giphy.com/v1/gifs/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();


        service = restAdapter.create(GiphyServiceInterface.class);

        loadForRandom();

    }
    private void loadForRandom(){
        String searchParameter = mMoodModel.searchTags.replace(",","+");
        service.search(searchParameter, new Callback<GiphyModel>() {

            @Override
            public void success(GiphyModel o, Response response) {
                loadRandom(o);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MainActivity", error.getMessage());
                hideLoading();
                somethingWentWrong();

            }
        });
    }

    private void loadRandom(GiphyModel o){
        int offset = randInt(0,o.mGiphyPagination.totalCount);
        String searchParameter = mMoodModel.searchTags.replace(",","+");
        service.searchRandom(searchParameter, offset, new Callback<GiphyModel>() {

            @Override
            public void success(GiphyModel o, Response response) {
                mSimpleModel = o;
                if (!mSimpleModel.mGiphyData.isEmpty()) {
                    mWebView.loadUrl(mSimpleModel.mGiphyData.get(0).images.image.mUrl);
                }
                hideLoading();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("MainActivity", error.getMessage());
                hideLoading();
                somethingWentWrong();

            }
        });
    }
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public void onResume() {
        super.onResume();
        load();
        btShare.startAnimation(animScale);
    }

    public class DownloadFilesTask extends AsyncTask<URL, Integer, File> {

        protected File doInBackground(URL... urls) {
            ((BasicActivity) getActivity()).showActionBarSpinner();
            int count = urls.length;
            long totalSize = 0;
            File tmp = null;
            for (int i = 0; i < count; i++) {
                tmp = FileDownloader.download(getActivity(), AppConstants.IMG_NAME, ".gif", urls[i]);
                if (tmp != null) {
                    totalSize += tmp.length();
                    Log.d(TAG, "Downloaded total: " + totalSize + " url: " + urls[i].toString());
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
            Log.d(TAG, "Download done, sharing file: " + path);
            try {
                sendFileWithUri(result);
            } catch (Exception e) {
                Log.e(TAG, "ups: " + e.getMessage());
                e.printStackTrace();
                ((BasicActivity) getActivity()).hideActionBarSpinner();
            }
            ((BasicActivity) getActivity()).hideActionBarSpinner();
        }
    }

    private void showLoading(){
        mLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        mLoading.setVisibility(View.GONE);
    }
    private void sendFileWithUri(File image) throws Exception {
        String message = String.valueOf(mTextEdit.getText());

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("image/*");

        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(image));
        startActivity(sendIntent);
    }

}
