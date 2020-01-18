package org.strawberryforum.argentum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.graphics.Bitmap;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    public WebView mWebView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (!DetectConnection.checkInternetConnection(this)) {
            mWebView = findViewById(R.id.webview);
            mWebView.setWebViewClient(new myWebClient());
            mWebView.setWebChromeClient(new myWebChromeClient());
            mWebView.loadUrl("file:///android_res/raw/noconnection.html");
            progressBar = findViewById(R.id.main_progress_bar);
            progressBar.setMax(100);
        } else {
            mWebView = findViewById(R.id.webview);
            mWebView.setWebViewClient(new myWebClient());
            mWebView.setWebChromeClient(new myWebChromeClient());
            mWebView.loadUrl("https://www.americangirl.com/shop/");
            progressBar = findViewById(R.id.main_progress_bar);
            progressBar.setMax(100);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true); //vulnerable, but nobody cares
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottomDolls:
                if (!DetectConnection.checkInternetConnection(this)) {
                    mWebView = findViewById(R.id.webview);
                    mWebView.loadUrl("file:///android_res/raw/noconnection.html");
                } else {
                    mWebView.loadUrl("https://www.americangirl.com/shop/c/dolls");
                }
                break;
            case R.id.bottomClothes:
                if (!DetectConnection.checkInternetConnection(this)) {
                    mWebView = findViewById(R.id.webview);
                    mWebView.loadUrl("file:///android_res/raw/noconnection.html");
                } else {
                    mWebView.loadUrl("https://www.americangirl.com/shop/c/clothing");
                }
                break;
            case R.id.bottomAccessories:
                if (!DetectConnection.checkInternetConnection(this)) {
                    mWebView = findViewById(R.id.webview);
                    mWebView.loadUrl("file:///android_res/raw/noconnection.html");
                } else {
                    mWebView.loadUrl("https://www.americangirl.com/shop/c/furniture-accessories");
                }
                break;
            case R.id.bottomBooks:
                if (!DetectConnection.checkInternetConnection(this)) {
                    mWebView = findViewById(R.id.webview);
                    mWebView.loadUrl("file:///android_res/raw/noconnection.html");
                } else {
                    mWebView.loadUrl("https://www.americangirl.com/shop/c/bookstore");
                }
                break;
            case R.id.bottomAbout:
                    mWebView.loadUrl("file:///android_res/raw/about.html");
            break;
        }
        return true;
    }

    public class myWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress); //tell the progress bar to fill up
        }
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            moveTaskToBack(true);
            finish();
        }
    }
}
