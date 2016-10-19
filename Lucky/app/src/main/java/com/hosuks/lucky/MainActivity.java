package com.hosuks.lucky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FCM 구현
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setBackgroundColor(0); //배경색
        mWebView.setHorizontalScrollBarEnabled(false); //가로 스크롤
        mWebView.setVerticalScrollBarEnabled(false); //세로 스크롤
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.loadUrl("http://ec2-52-78-110-63.ap-northeast-2.compute.amazonaws.com:5001/");
        mWebView.setWebViewClient(new WishWebViewClient());

        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WishWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
