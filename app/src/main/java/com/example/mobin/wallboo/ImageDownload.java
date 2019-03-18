package com.example.mobin.wallboo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageDownload extends AsyncTask<String, Void, Bitmap> {

    ImageView image;

    public ImageDownload(ImageView image) {
        this.image = image;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urldisplay = strings[0];
        Bitmap temp = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            temp = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", "doInBackground: " + e.getMessage());
            e.printStackTrace();
        }
        return temp;
    }

    protected void onPostExecute(Bitmap result) {
        image.setImageBitmap(result);
    }
}
