package com.example.screenshot;

public interface ScreenshotWatcher {
    public static interface ScreenshotCallback {
        public void onScreenshot(String path);
    }
    public void startWatching();
    public void stopWatching();
    public boolean isWatching();
}
