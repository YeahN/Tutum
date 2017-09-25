package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.ryn41.tutum.R;
import com.example.ryn41.tutum.etc.TempData;
import com.example.ryn41.tutum.iamportsdk.InicisWebViewClient;

public class WebViewActivity extends Activity {

    private String payMethod = "";
    private int amount = 0;
    private String tel = "";
    private WebView mWebView;
    private static final String APP_SCHEME = "tutumandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        payMethod = intent.getStringExtra("payMethod");
        amount = intent.getIntExtra("amount", 0);
        tel = intent.getStringExtra("tel");
    }

    @Override
    public void onResume() {
        super.onResume();
        makeView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void makeView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new InicisWebViewClient(this));
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(mWebView, true);
        }

        Intent intent = getIntent();
        Uri intentData = intent.getData();

        if ( intentData == null ) {
            mWebView.loadUrl("http://13.59.135.92/payment.php?userID=" + TempData.getID() + "&method=" + payMethod + "&amount=" + amount + "&tel=" + tel);
        } else {
            //isp 인증 후 복귀했을 때 결제 후속조치
            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length() + 3);
                mWebView.loadUrl(redirectURL);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String url = intent.toString();
        if ( url.startsWith(APP_SCHEME) ) {
            String redirectURL = url.substring(APP_SCHEME.length() + 3);
            mWebView.loadUrl(redirectURL);
        }
    }
}
