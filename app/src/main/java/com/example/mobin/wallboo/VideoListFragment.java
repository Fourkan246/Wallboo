package com.example.mobin.wallboo;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.dingmouren.videowallpaper.VideoWallpaper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {

    protected AbsListView listView;

    public VideoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video_list, container, false);

        listView = (GridView) rootView.findViewById(R.id.grid);

        //listView.getLayoutParams();

        VideoAdapter videoAdapter = new VideoAdapter(getActivity());
        listView.setAdapter(videoAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startImagePagerActivity(position);
                Toast.makeText(getActivity(), "" + position, Toast.LENGTH_LONG).show();

                VideoWallpaper videoWallpaper = new VideoWallpaper();
                videoWallpaper.setToWallPaper(getContext(), videoAdapter.videolist.get(position));

//                Intent intent = new Intent(getActivity(), WallpaperShowActivity.class);
//                intent.putExtra("position", position);
//                startActivity(intent);


            }
        });

        return rootView;
    }


    private static class VideoAdapter extends BaseAdapter {

        int i = 65;
        private static String[] IMAGE_URLS;
        private ArrayList<String> videolist = new ArrayList<>();
        private LayoutInflater inflater;

        private DisplayImageOptions options;
        private HashMap<String, String> nameTouri = new HashMap<>();
        private ArrayList<String> fake = new ArrayList<>();

        VideoAdapter(Context context) {


//            IMAGE_URLS = (String[]) videolist.toArray();

            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_stub)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .cacheInMemory(false)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();


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

            Toast.makeText(context, videolist.size() + " ala", Toast.LENGTH_LONG).show();

            for (String uri : videolist) {
                fake.add(i + ".png");
                nameTouri.put(i + "", uri);
                Glide.with(context)
                        .load(uri)
                        .asBitmap()
                        .thumbnail(0.1f)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                FileOutputStream fos = null;
                                try {
                                    fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" +
                                            i + ".png"));
                                    i++;

                                    resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                    fos.flush();
                                    fos.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }

        }

        @Override
        public int getCount() {
            return videolist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.item_grid_image, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = view.findViewById(R.id.image);
                holder.progressBar = view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

//            while (i != 65 + videolist.size()) {
//                try {
//                    Thread.sleep(20);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            ImageLoader.getInstance()
                    .displayImage("file://" + Environment.getExternalStorageDirectory() + "/" + fake.get(position), holder.imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            holder.progressBar.setProgress(0);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            holder.progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });

            return view;
        }
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }


    private void transition(View view) {

        if (Build.VERSION.SDK_INT < 21) {
        } else {

            Intent intent = new Intent(this.getContext(), DemoFragment.class);

            ActivityOptionsCompat options = ActivityOptionsCompat.

                    makeSceneTransitionAnimation(this.getActivity(), view, getString(R.string.app_name));

//            startActivity(intent, options.toBundle());

        }

    }

}
