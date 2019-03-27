package com.example.mobin.wallboo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.videowallpaper.VideoWallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import im.ene.toro.widget.PressablePlayerSelector;

/**
 * @author eneim (7/1/17).
 */

class BasicListAdapter extends RecyclerView.Adapter<BasicPlayerViewHolder> {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") //
    private MediaList mediaList;

    @Nullable
    private final PressablePlayerSelector selector;

    BasicListAdapter(@Nullable PressablePlayerSelector selector, @NonNull Context context) {
        this.selector = selector;
        mediaList = new MediaList(context);
    }

    @NonNull
    @Override
    public BasicPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(BasicPlayerViewHolder.LAYOUT_RES, parent, false);


        BasicPlayerViewHolder viewHolder = new BasicPlayerViewHolder(view, this.selector);
        if (this.selector != null) viewHolder.itemView.setOnLongClickListener(this.selector);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoWallpaper videoWallpaper = new VideoWallpaper();
                videoWallpaper.setToWallPaper(view.getContext(), mediaList.getURI(0));
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasicPlayerViewHolder holder, int position) {
        holder.bind(mediaList.get(position));

//        PlayerView playerView = holder.itemView.findViewById(R.id.player);

//        CardView cardView = holder.itemView.findViewById(R.id.card);
//
//        holder.getPlayerView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VideoWallpaper videoWallpaper = new VideoWallpaper();
//                videoWallpaper.setToWallPaper(holder.itemView.getContext(), holder.mediaUri.toString());
//            }
//        });

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
