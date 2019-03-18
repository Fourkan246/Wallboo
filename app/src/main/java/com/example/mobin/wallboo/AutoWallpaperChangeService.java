package com.example.mobin.wallboo;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class AutoWallpaperChangeService extends Service {

    private Handler handler = new Handler();
    Context context;
    Random rd = new Random();

    public AutoWallpaperChangeService(Context context) {
        this.context = context;
        Log.i("timerservice", "AutoWallpaperChangeService: created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    public AutoWallpaperChangeService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    TimerTask timerTask;
    Timer timer = null;

    void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "alal", Toast.LENGTH_SHORT).show();
                Log.e("timerservice", "run: repeat");
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    long r = rd.nextLong();
                    if (r % 3 == 0) {
                        wallpaperManager.setResource(R.drawable.shop1);
                    } else if (r % 2 == 0) {
                        wallpaperManager.setResource(R.drawable.shop4);
                    } else {
                        wallpaperManager.setResource(R.drawable.shop6);
                    }
                } catch (Exception e) {

                }

//                    }
//                });
            }
        };

        timer.schedule(timerTask, 1000, 5000);
    }

    void stoptimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("timerservice", "onDestroy: called");
        Intent intent1 = new Intent("auto.wallpaper.service.restart");
        sendBroadcast(intent1);
        stoptimer();
    }
}
