package com.yu.network;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yu.network.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        WebView webView = (WebView) findViewById(R.id.id_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        if (link.startsWith("http://")) {
            webView.loadUrl(link);
        } else {
            webView.loadUrl("http://"+link);
        }

    }
}
