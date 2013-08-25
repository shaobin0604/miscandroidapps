package io.github.shaobin0604.webviewtest;

import android.os.Bundle;
import android.webkit.WebViewClient;

public class WithDefaultWebViewClientActivity extends WithoutWebViewClientActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void overrideSettings() {
        mWebView.setWebViewClient(new WebViewClient());
    }
}
