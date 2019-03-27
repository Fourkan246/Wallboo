package com.example.mobin.wallboo.WallpaperPreviewList;

import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.mobin.wallboo.Constants;
import com.example.mobin.wallboo.LiveWallpaperService;
import com.example.mobin.wallboo.R;
import com.example.mobin.wallboo.utils.Constant;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


public class WallpaperShowActivity extends AppCompatActivity implements DiscreteScrollView.OnItemChangedListener,
        View.OnClickListener {

    private List<Item> data = new ArrayList<>();
    private Wallpapers shop;
    private int oldpicture = 0;
    private SharedPreferences.Editor editor;

    private TextView currentItemName;
    private TextView currentItemPrice;
    private ImageView rateItemButton;
    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter infiniteAdapter;

    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        StatusBarUtil.setTranslucent(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WallpaperShowActivity.this);
        editor = sharedPreferences.edit();
        editor.putBoolean("clickporse", false);

        intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        int pos = (int) bundle.get("position");

        currentItemName = findViewById(R.id.item_name);
        currentItemPrice = findViewById(R.id.item_price);
        rateItemButton = findViewById(R.id.item_btn_rate);

        shop = Wallpapers.get();

        for (int i = pos; i < Constants.IMAGES.length && i < pos + 50; i++) {
            data.add(new Item(1, "abcd", "120$", Constants.IMAGES[i]));
        }

        itemPicker = findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new WallpaperShowAdapter(data));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(150);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        onItemChanged(data.get(0));

        findViewById(R.id.item_btn_rate).setOnClickListener(this);
        findViewById(R.id.item_btn_buy).setOnClickListener(this);
        findViewById(R.id.item_btn_comment).setOnClickListener(this);
        findViewById(R.id.home).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                finish();
                break;
            case R.id.item_btn_buy:
                try {
                    WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                    if (wm.getWallpaperInfo() == null || !wm.getWallpaperInfo().getPackageName().equals(this
                            .getPackageName())) {
                        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(getApplicationContext(), LiveWallpaperService.class));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        FileOutputStream fos = null;
                        try {
                            fos = this.openFileOutput(
                                    Constant.CACHE, Context.MODE_PRIVATE);
                            currentlyLoaded.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (Exception e) {

                        }
                        startActivity(intent);
                        Toast.makeText(this, "NOT IN THE MOOD :/", Toast.LENGTH_LONG).show();
                    } else {
                        FileOutputStream fos = null;
                        try {
                            fos = this.openFileOutput(
                                    Constant.CACHE, Context.MODE_PRIVATE);
                            currentlyLoaded.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (Exception e) {

                        }
                        editor.putBoolean("clickporse", true);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "mood kharap kore dilo :3", Toast.LENGTH_SHORT).show();
                    }

                } catch (ActivityNotFoundException e) {
                    try {
                        startActivity(new Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } catch (ActivityNotFoundException e2) {
                        Toast.makeText(this, "toasting everything :/", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                showUnsupportedSnackBar();
                break;
        }
    }

    private void onItemChanged(Item item) {
        currentItemName.setText(item.getName());
        currentItemPrice.setText(item.getPrice());
        changeRateButtonState(item);
    }

    private void changeRateButtonState(Item item) {
    }

    private SharedPreferences sharedPreferences;

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int position) {
        int positionInDataSet = infiniteAdapter.getRealPosition(position);
        onItemChanged(data.get(positionInDataSet));
        String path = data.get(positionInDataSet).getImage();

        Handler handler = new Handler(this.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                backDraw(path);
            }
        };
        handler.post(runnable);
    }

    Bitmap currentlyLoaded = null;

    private void backDraw(String path) {
        Glide.with(this.getApplicationContext())
                .load(path)
                .asBitmap()
                .centerCrop()
                .thumbnail(.1f)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        currentlyLoaded = Bitmap.createBitmap(resource);
                        findViewById(R.id.draw_here).setBackground(new BitmapDrawable(resource));
                    }
                });
    }

    private void showUnsupportedSnackBar() {
        Snackbar.make(itemPicker, "Unsupported Operation", Snackbar.LENGTH_SHORT).show();
    }
}
