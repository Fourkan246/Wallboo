package com.example.mobin.wallboo;


import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.dingmouren.videowallpaper.VideoWallpaper;
import com.google.android.exoplayer2.ui.PlayerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerDispatcher;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.helper.ToroPlayerHelper;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;
import im.ene.toro.widget.PressablePlayerSelector;

/**
 * @author eneim (7/1/17).
 */

@SuppressWarnings({"WeakerAccess", "unused"})
        //
class BasicPlayerViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

    private static final String TAG = "Toro:Basic:Holder";

    static final int LAYOUT_RES = R.layout.view_holder_exoplayer_basic;

    ToroPlayerHelper helper;
    Uri mediaUri;

    @BindView(R.id.player)
    PlayerView playerView;


    public BasicPlayerViewHolder(View itemView, PressablePlayerSelector selector) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        if (selector != null)
            playerView.setControlDispatcher(new ExoPlayerDispatcher(selector, this));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoWallpaper videoWallpaper = new VideoWallpaper();
                videoWallpaper.setToWallPaper(itemView.getContext(), mediaUri.toString());
                Toast.makeText(itemView.getContext(), "paise ;/", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public View getPlayerView() {
        return playerView;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();
    }

    @Override
    public void initialize(@NonNull Container container, @NonNull PlaybackInfo playbackInfo) {
        if (helper == null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
        }
        helper.initialize(container, playbackInfo);
    }

    @Override
    public void play() {
        if (helper != null) helper.play();
    }

    @Override
    public void pause() {
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (helper != null) {
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }

    @Override
    public String toString() {
        return "ExoPlayer{" + hashCode() + " " + getAdapterPosition() + "}";
    }

    void bind(Content.Media media) {
        this.mediaUri = media.mediaUri;
    }
}
