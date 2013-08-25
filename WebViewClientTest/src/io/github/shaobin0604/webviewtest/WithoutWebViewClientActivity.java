
package io.github.shaobin0604.webviewtest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;

public class WithoutWebViewClientActivity extends Activity {
    protected static final String WEB_SITE = "http://v5kanshu.com";

    protected WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        String databasePath = getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabasePath(databasePath);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Set cache size to 8 mb by default. should be more than enough
        settings.setAppCacheMaxSize(1024 * 1024 * 8);

        // This next one is crazy. It's the DEFAULT location for your app's
        // cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String appCachePath = getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
                    long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
                // TODO Auto-generated method stub
                quotaUpdater.updateQuota(estimatedDatabaseSize * 2);
            }

            @Override
            public void onReachedMaxAppCacheSize(long requiredStorage, long quota,
                    QuotaUpdater quotaUpdater) {
                // TODO Auto-generated method stub
                quotaUpdater.updateQuota(requiredStorage * 2);
            }

        });

        overrideSettings();

        mWebView.loadUrl(WEB_SITE);
    }

    /**
     * subclass should override this method to do more settings
     */
    protected void overrideSettings() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
