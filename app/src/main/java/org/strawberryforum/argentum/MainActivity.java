package org.strawberryforum.argentum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    //Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://www.americangirl.com/shop/");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); //vulnerable, but nobody cares

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottomDolls:
                //bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                mWebView.loadUrl("https://www.americangirl.com/shop/c/dolls");
                break;
            case R.id.bottomClothes:
                //bottomNavigationView.setItemBackgroundResource(R.color.colorRed500);
                mWebView.loadUrl("https://www.americangirl.com/shop/c/clothing");
                break;
            case R.id.bottomAccessories:
                //bottomNavigationView.setItemBackgroundResource(R.color.colorRed500);
                mWebView.loadUrl("https://www.americangirl.com/shop/c/furniture-accessories");
                break;
            case R.id.bottomBooks:
                //bottomNavigationView.setItemBackgroundResource(R.color.colorRed500);
                mWebView.loadUrl("https://www.americangirl.com/shop/c/bookstore");
                break;
            case R.id.bottomAbout:
                //bottomNavigationView.setItemBackgroundResource(R.color.colorBrown500);
                mWebView.loadUrl("file:///android_res/raw/about.html");
            break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        mWebView.goBack();
    }
}
