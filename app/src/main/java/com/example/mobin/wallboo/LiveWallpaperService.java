package com.example.mobin.wallboo;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.mobin.wallboo.net.rbgrn.android.glwallpaperservice.GLWallpaperService;
import com.example.mobin.wallboo.sensor.RotationSensor;

public class LiveWallpaperService extends GLWallpaperService {
    public static final int SENSOR_RATE = 60;
    private final static String TAG = "LiveWallpaperService";
//    private Bitmap thiswallpaper = null;

    @Override
    public Engine onCreateEngine() {
//        MyEngine lala = new MyEngine();
//        lala.curr = thiswallpaper;
        return new MyEngine();
    }

    private class MyEngine extends GLEngine implements LiveWallpaperRenderer.Callbacks,
            SharedPreferences.OnSharedPreferenceChangeListener, RotationSensor.Callback {
        private SharedPreferences preference;
        //        private Bitmap curr = null;
        private LiveWallpaperRenderer renderer;
        private RotationSensor rotationSensor;
        private BroadcastReceiver powerSaverChangeReceiver;
        private boolean pauseInSavePowerMode = false;
        private boolean savePowerMode = false;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 0, 0, 0);
            renderer = new LiveWallpaperRenderer(LiveWallpaperService.this.getApplicationContext
                    (), this);
//            renderer.thisImage = curr;
            setRenderer(renderer);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
            rotationSensor = new RotationSensor(LiveWallpaperService.this.getApplicationContext()
                    , this, SENSOR_RATE);
            preference = PreferenceManager.getDefaultSharedPreferences(LiveWallpaperService.this);
//            Intent intent = this.get
            preference.registerOnSharedPreferenceChangeListener(this);
            renderer.setBiasRange(preference.getInt("range", 10));
            renderer.setDelay(21 - preference.getInt("deny", 10));
            renderer.setScrollMode(preference.getBoolean("scroll", true));
            renderer.setIsDefaultWallpaper(preference.getInt("default_picture", 0) == 0);
            setPowerSaverEnabled(preference.getBoolean("power_saver", true));

        }

        @Override
        public void onDestroy() {
            // Unregister this as listener
            rotationSensor.unregister();
            if (Build.VERSION.SDK_INT >= 21) {
                unregisterReceiver(powerSaverChangeReceiver);
            }
            preference.unregisterOnSharedPreferenceChangeListener(this);
            // Kill renderer
            if (renderer != null) {
                renderer.release(); // assuming yours has this method - it
                // should!
            }
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (!pauseInSavePowerMode || !savePowerMode) {
                if (visible) {
                    rotationSensor.register();
                    renderer.startTransition();
                } else {
                    rotationSensor.unregister();
                    renderer.stopTransition();
                }
            } else {
                if (visible) {
                    renderer.startTransition();
                } else {
                    renderer.stopTransition();
                }
            }
        }

        void setPowerSaverEnabled(boolean enabled) {
            if (pauseInSavePowerMode == enabled) return;
            pauseInSavePowerMode = enabled;
            if (Build.VERSION.SDK_INT >= 21) {
                final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (pauseInSavePowerMode) {
                    powerSaverChangeReceiver = new BroadcastReceiver() {
                        @TargetApi(21)
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            savePowerMode = pm.isPowerSaveMode();
                            if (savePowerMode && isVisible()) {
                                rotationSensor.unregister();
                                renderer.setOrientationAngle(0, 0);
                            } else if (!savePowerMode && isVisible()) {
                                rotationSensor.register();
                            }
                        }
                    };

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
                    registerReceiver(powerSaverChangeReceiver, filter);
                    savePowerMode = pm.isPowerSaveMode();
                    if (savePowerMode && isVisible()) {
                        rotationSensor.unregister();
                        renderer.setOrientationAngle(0, 0);
                    }
                } else {
                    unregisterReceiver(powerSaverChangeReceiver);
                    savePowerMode = pm.isPowerSaveMode();
                    if (savePowerMode && isVisible()) {
                        rotationSensor.register();
                    }

                }
            }
        }


        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                     int yPixelOffset) {
            if (!isPreview()) {
                renderer.setOffset(xOffset, yOffset);
                renderer.setOffsetStep(xOffsetStep, yOffsetStep);
                Log.i(TAG, xOffset + ", " + yOffset + ", " + xOffsetStep + ", " + yOffsetStep);
            }
        }

        @Override
        public void requestRender() {
            super.requestRender();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
//                case "range":
//                    renderer.setBiasRange(sharedPreferences.getInt(key, 10));
//                    break;
//                case "delay":
//                    renderer.setDelay(21 - sharedPreferences.getInt(key, 10));
//                    break;
//                case "scroll":
//                    renderer.setScrollMode(sharedPreferences.getBoolean(key, true));
//                    break;
//                case "power_saver":
//                    setPowerSaverEnabled(sharedPreferences.getBoolean(key, true));
//                    break;
                case "clickporse":
                    if (sharedPreferences.getBoolean("clickporse", true))
                        renderer.setIsDefaultWallpaper(false);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSensorChanged(float[] angle) {
            if (getResources().getConfiguration().orientation == Configuration
                    .ORIENTATION_LANDSCAPE)
                renderer.setOrientationAngle(angle[1], angle[2]);
            else renderer.setOrientationAngle(-angle[2], angle[1]);
        }
    }

}
