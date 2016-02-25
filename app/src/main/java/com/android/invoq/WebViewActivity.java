package com.android.invoq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by user on 23/02/2016.
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView web_view;
    private String contents;
    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.web_view_activity);

        Intent intent = getIntent();
        //gets scanned content
        contents = intent.getStringExtra("scanned_result");

        web_view = new WebView(this);
        setContentView(web_view);

        WebSettings settings = web_view.getSettings();
        settings.setJavaScriptEnabled(true);

        mProgress = ProgressDialog.show(this,"Loading", "Please wait for a moment...");
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (mProgress.isShowing()) {
                    mProgress.dismiss();
                }
            }
        });
        if(contents.isEmpty())
        {
            Toast.makeText(WebViewActivity.this,"Please scan correct code",Toast.LENGTH_SHORT).show();
        }
        else {
            web_view.loadUrl(contents);
        }
    }
}