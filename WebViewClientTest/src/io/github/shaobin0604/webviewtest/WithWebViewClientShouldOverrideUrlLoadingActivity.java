
package io.github.shaobin0604.webviewtest;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WithWebViewClientShouldOverrideUrlLoadingActivity extends WithoutWebViewClientActivity {

    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void overrideSettings() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(WEB_SITE)) {
                    // internal site link, let webview handle it
                    return false;
                } else {
                    // external site link, let system handle it
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }
        });
    }
}
