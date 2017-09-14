package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.ryn41.tutum.R;

public class WebViewActivity extends Activity {

    private String payMethod = "";
    private int amount = 0;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
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
        Intent intent = getIntent();
        payMethod = intent.getStringExtra("payMethod");
        amount = intent.getIntExtra("amount", 0);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://13.59.135.92/iamport.php?method="+payMethod+"&amount="+amount);
    }
}
