
package com.example.screenshot;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;

public class MediaObserverScreenshotWatcher implements ScreenshotWatcher, Callback {
    private static final int WHAT_INIT = 0;
    private static final int WHAT_CHANGED = 1;
    private static final int WHAT_NOTIFY = 2;

    private static final Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private static final String[] PROJECTION = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
    };

    private static final String SELECTION = MediaStore.Images.Media.DISPLAY_NAME
            + " like 'Screenshot%'";

    private static final String SORT_ORDER = MediaStore.Images.Media.DATE_ADDED + " desc limit 1";

    protected static final String TAG = MediaObserverScreenshotWatcher.class.getSimpleName();

    private Context mContext;

    private ScreenshotCallback mScreenshotCallback;

    private ContentObserver mContentObserver;

    private Handler mMainHandler;

    private Handler mQueryHandler;

    private HandlerThread mHandlerThread;

    private volatile long mLatestScreenshotId;
    private boolean mIsWatching;

    public MediaObserverScreenshotWatcher(Context context, ScreenshotCallback callback) {
        super();
        mContext = context;
        mScreenshotCallback = callback;
        mMainHandler = new Handler(Looper.getMainLooper(), this);
        mHandlerThread = new HandlerThread("MediaObserverScreenshotWatcher",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        mHandlerThread.start();
        mQueryHandler = new Handler(mHandlerThread.getLooper(), this);
        mContentObserver = new MediaObserver(mMainHandler);
        mQueryHandler.sendEmptyMessage(WHAT_INIT);
    }

    @Override
    public void startWatching() {
        if (mScreenshotCallback != null) {
            mContext.getContentResolver().registerContentObserver(EXTERNAL_CONTENT_URI, true,
                    mContentObserver);
            mIsWatching = true;
        }
    }

    @Override
    public void stopWatching() {
        mContext.getContentResolver().unregisterContentObserver(mContentObserver);
        mIsWatching = false;
    }

    @Override
    public boolean isWatching() {
        return mIsWatching;
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG, "handleMessage E, what: " + msg.what);
        switch (msg.what) {
            case WHAT_INIT: {
                Pair<Long, String> latestScreenshot = queryLatestScreenshot();
                if (latestScreenshot != null) {
                    mLatestScreenshotId = latestScreenshot.first;
                }
                return true;
            }
            case WHAT_CHANGED: {
                Pair<Long, String> latestScreenshot = queryLatestScreenshot();
                if (latestScreenshot != null && latestScreenshot.first > mLatestScreenshotId) {
                    mLatestScreenshotId = latestScreenshot.first;
                    mMainHandler.obtainMessage(WHAT_NOTIFY, latestScreenshot.second).sendToTarget();
                }
                return true;
            }
            case WHAT_NOTIFY: {
                String path = (String) msg.obj;
                mScreenshotCallback.onScreenshot(path);
                return true;
            }
            default: {
                return false;
            }
        }
    }

    private Pair<Long, String> queryLatestScreenshot() {
        // screenshot <id, path>
        Pair<Long, String> screenshot = null;
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(EXTERNAL_CONTENT_URI, PROJECTION,
                    SELECTION, null, SORT_ORDER);
            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(0);
                String path = cursor.getString(1);
                screenshot = new Pair<Long, String>(id, path);
            }
        } catch (Exception e) {
            Log.e(TAG, "queryLatestScreenshot e");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return screenshot;
    }

    private class MediaObserver extends ContentObserver {

        public MediaObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            mQueryHandler.sendEmptyMessage(WHAT_CHANGED);
        }
    }
}
