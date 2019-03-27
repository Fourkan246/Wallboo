package com.example.mobin.wallboo;


import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashSet;

import androidx.loader.content.CursorLoader;

/**
 * A special {@link ArrayList}
 *
 * @author eneim (7/1/17).
 */

final class MediaList extends ArrayList<Content.Media> {

    private Context context;
    private ArrayList<String> videolist = new ArrayList<>();
    int i = 65, ii = 0;
//    private ArrayList<String> fake = new ArrayList<>();

    MediaList(Context context, ArrayList<String> videolist) {
        this.context = context;

        Content.ITEMS = new String[videolist.size() + 1];
        ii = 0;
        for (String uri : videolist) {
            Content.ITEMS[ii] = uri;
            ii++;
        }
        Content.ITEMS[ii] = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
//        govt();

    }

    void govt() {
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = new CursorLoader(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null).loadInBackground();

        HashSet<String> vdlist = new HashSet<>();

        try {
            cursor.moveToFirst();
            do {
                vdlist.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        videolist = new ArrayList<>(vdlist);

        Content.ITEMS = new String[videolist.size() + 1];
        ii = 0;
        for (String uri : videolist) {
            Content.ITEMS[ii] = uri;
            ii++;
        }
        Content.ITEMS[ii] = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";

//        Log.e("getview", "govt: " + ii);

    }

    @Override
    public int size() {
        return Content.ITEMS.length;
    }

    @Override
    public Content.Media get(int index) {
        return Content.Media.getItem(index);
    }

    public String getURI(int position) {
        return Content.ITEMS[position];
    }

    @Override
    public int indexOf(Object o) {
        return o instanceof Content.Media ? ((Content.Media) o).index : -1;
    }

    @Override
    public boolean add(Content.Media media) {
        throw new UnsupportedOperationException("Unsupported");
    }

    @Override
    public Content.Media remove(int index) {
        throw new UnsupportedOperationException("Unsupported");
    }
}
