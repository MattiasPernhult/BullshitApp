package com.fam.fam.bullshitapp;

/**
 * Created by Felix on 2015-10-13.
 */
import android.os.AsyncTask;
import android.util.Log;

public class GifDataDownloader extends AsyncTask<String, Void, byte[]> {

    private static final String TAG = "GifDataDownloader";

    public GifDataDownloader() {
    }

    @Override
    protected byte[] doInBackground(final String... params) {
        final String gifUrl = params[0];

        if (gifUrl == null)
            return null;

        byte[] gif = null;
        try {
            gif = ByteArrayHttpClient.get(gifUrl);
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "GifDecode OOM: " + gifUrl, e);
        }

        return gif;
    }
}
