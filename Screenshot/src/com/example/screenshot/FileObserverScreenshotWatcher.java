package com.example.screenshot;

import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class FileObserverScreenshotWatcher implements ScreenshotWatcher {
    private static final String TAG = FileObserverScreenshotWatcher.class.getSimpleName();
    private final String mScreenshotDir;
    private final FileObserver mFileObserver;
    private final ScreenshotCallback mScreenshotCallback;
    private final Handler mMainHandler;
    private boolean mIsWatching;

    public FileObserverScreenshotWatcher(String screenshotDir, ScreenshotCallback callback) {
        Log.d(TAG, "FileObserverScreenshotWatcher E, screenshot dir: " + screenshotDir);
        mScreenshotDir = screenshotDir;
        mScreenshotCallback = callback;
        mMainHandler = new Handler(Looper.getMainLooper());
        mFileObserver = new FileObserver(mScreenshotDir, FileObserver.CREATE) {

            @Override
            public void onEvent(int event, final String path) {
                if (mScreenshotCallback != null && (event & FileObserver.CREATE) != 0) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mScreenshotCallback.onScreenshot(path);
                        }
                    });
                }
            }
        };
    }

    @Override
    public void startWatching() {
        mFileObserver.startWatching();
        mIsWatching = true;
    }

    @Override
    public void stopWatching() {
        mFileObserver.stopWatching();
        mIsWatching = false;
    }

    @Override
    public boolean isWatching() {
        return mIsWatching;
    }
}
