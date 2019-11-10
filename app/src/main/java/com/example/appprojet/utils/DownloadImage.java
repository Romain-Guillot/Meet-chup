package com.example.appprojet.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * TODO : alpha version, DO NOT USE IT.
 */
@Deprecated
@SuppressWarnings("all")
public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView image;

    public DownloadImage(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        image.setImageBitmap(result);
    }
}