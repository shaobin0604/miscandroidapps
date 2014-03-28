
package com.example.screenshot;

import java.io.File;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.example.screenshot.ScreenshotWatcher.ScreenshotCallback;

public class WatchService extends Service {
    private static final String TAG = WatchService.class.getSimpleName();

    public static final String EXTRA_WATCH_MODE = "watch_mode";

    public static final int WATCH_MODE_FILE_OBSERVER = 0;

    public static final int WATCH_MODE_MEDIA_STORE = 1;

    private ScreenshotWatcher mWatcher;

    private ScreenshotCallback mCallback;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            mCallback = new ScreenshotCallback() {

                @Override
                public void onScreenshot(String path) {
                    Toast.makeText(WatchService.this, "path: " + path, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };
            int watchMode = intent.getIntExtra(EXTRA_WATCH_MODE, WATCH_MODE_FILE_OBSERVER);
            if (watchMode == WATCH_MODE_FILE_OBSERVER) {
                File pictureDir = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String screenshotDir = new File(pictureDir, "Screenshots").getAbsolutePath();
                mWatcher = new FileObserverScreenshotWatcher(screenshotDir, mCallback);
                mWatcher.startWatching();
            } else {
                mWatcher = new MediaObserverScreenshotWatcher(this, mCallback);
                mWatcher.startWatching();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatcher.stopWatching();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}
