package com.example.mobin.wallboo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class test_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });

        findViewById(R.id.mainactivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call1();
            }
        });
    }

    private void call() {
        startService(new Intent(this, AutoWallpaperChangeService.class));
    }

    private void call1() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
