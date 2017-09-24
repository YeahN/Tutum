package com.example.ryn41.tutum.etc;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by godjakoo on 2017. 9. 14..
 */

public class TutumJavascriptInterface {
    private WebView mWebView= null;
    private Context mContext= null;

    public TutumJavascriptInterface(WebView v, Context context){
        mWebView= v;
        mContext= context;
    }

    @JavascriptInterface
    public void addPayMethod(String method){

    }
    @JavascriptInterface
    public void addAmount(int amount){

    }
}
