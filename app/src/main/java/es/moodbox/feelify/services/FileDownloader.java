package es.moodbox.feelify.services;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by victoriza on 17/01/15.
 */
public class FileDownloader {

    private final static String TAG = FileDownloader.class.getSimpleName();

    public static File download(Context context, String filename, String fileExtension, URL url) {

        File file = null;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Download error ");
                return null;
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            Log.e(TAG, "Download file length "+fileLength);

            // download the file
            input = connection.getInputStream();
            file = File.createTempFile(filename, fileExtension, context.getExternalCacheDir());

            output = new FileOutputStream(file);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Log.d(TAG, "Downloading : "+total+ " total : "+total);
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            Log.e(TAG, "Download file error "+e.getMessage());
            return null;
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return file;
    }
}
