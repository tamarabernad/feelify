package es.moodbox.mooding.utils;

/**
 * Created by machete on 1/11/15.
 */
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

public class DLManager {
    public static DownloadManager manger;

    @SuppressLint("NewApi")
    public static DownloadManager useDownloadManager(String url, String dir, String name, Context c) {
        DownloadManager dm = (DownloadManager) c
                .getSystemService(Context.DOWNLOAD_SERVICE);
        manger = dm;
        DownloadManager.Request dlrequest = new DownloadManager.Request(
                Uri.parse(url));
        dlrequest
                .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_WIFI
                                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(name)
                .setDescription("Download in progress")
                .setDestinationInExternalPublicDir(dir, name)
                .allowScanningByMediaScanner();

        dm.enqueue(dlrequest);
        return dm;

    }
}
