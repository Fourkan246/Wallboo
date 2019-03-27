package com.example.mobin.wallboo;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import im.ene.toro.widget.Container;
import im.ene.toro.widget.PressablePlayerSelector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToroVideoListFragment extends Fragment {


    public ToroVideoListFragment() {
        // Required empty public constructor
    }

    private Container container1;
    private LinearLayoutManager layoutManager;
    private BasicListAdapter adapter;
    private PressablePlayerSelector selector;
    private View view;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toro_video_list, container, false);

        this.view = view;
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        cursor = new CursorLoader(view.getContext(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null).loadInBackground();
        BackgroundVideoSearcher backgroundVideoSearcher = new BackgroundVideoSearcher();
        backgroundVideoSearcher.execute("lala", "lala", "lala");

        return view;
    }

    private class BackgroundVideoSearcher extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

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
            return "succeeded";
        }

        private ArrayList<String> videolist;

        @Override
        protected void onPreExecute() {
            Toast.makeText(view.getContext(), "starting to search videos :P", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            container1 = view.findViewById(R.id.player_container);

            layoutManager = new LinearLayoutManager(view.getContext());
            container1.setLayoutManager(layoutManager);
            selector = new PressablePlayerSelector(container1);
            container1.setPlayerSelector(selector);

            adapter = new BasicListAdapter(selector, view.getContext(), videolist);
            container1.setAdapter(adapter);

            Toast.makeText(view.getContext(), "we are finished :/", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        layoutManager = null;
        adapter = null;
        selector = null;
        super.onDestroyView();
    }
}
