package com.android.invoq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by user on 23/02/2016.
 */
public class WebViewActivity extends AppCompatActivity
{
    private WebView web_view;
    private String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);

        web_view = (WebView) findViewById(R.id.webView1);
        WebSettings webSettings = web_view.getSettings();
        webSettings.setJavaScriptEnabled(true);

        Intent intent = getIntent();
        contents = intent.getStringExtra("scanned_result");

        web_view.loadUrl(contents);
    }

}
