package com.example.ryn41.tutum.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.ryn41.tutum.R;

public class WebViewActivity extends Activity {

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
    public void onPause(){
        super.onPause();
    }

    private void makeView() {
        String payMethod;
        int amount;
        Intent intent = getIntent();
        payMethod = intent.getStringExtra("payMethod");
        amount = intent.getIntExtra("amount", 0);

        WebView web = (WebView) findViewById(R.id.webview);
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://13.59.135.92/iamport.html");
        web.loadUrl("javascript:setData('" + payMethod + "')");
    }
}
