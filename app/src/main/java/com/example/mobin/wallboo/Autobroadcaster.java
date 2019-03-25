package com.example.mobin.wallboo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Autobroadcaster extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("timerservice", "onReceive: stopped");
        context.startService(new Intent(context, AutoWallpaperChangeService.class));
    }
}
