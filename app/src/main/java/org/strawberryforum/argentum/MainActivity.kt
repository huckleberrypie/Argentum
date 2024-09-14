package org.strawberryforum.argentum

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.graphics.Bitmap

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationView: BottomNavigationView
    lateinit var mWebView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        mWebView = findViewById(R.id.webview)
        mWebView.webViewClient = MyWebClient()
        mWebView.webChromeClient = MyWebChromeClient()

        progressBar = findViewById(R.id.main_progress_bar)
        progressBar.max = 100

        if (!DetectConnection.checkInternetConnection(this)) {
            mWebView.loadUrl("file:///android_res/raw/noconnection.html")
        } else {
            mWebView.loadUrl("https://www.americangirl.com/shop/")
            val webSettings: WebSettings = mWebView.settings
            webSettings.javaScriptEnabled = true
            webSettings.domStorageEnabled = true
            webSettings.defaultTextEncodingName = "utf-8"
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottomDolls -> loadUrl("https://www.americangirl.com/shop/c/dolls")
            R.id.bottomClothes -> loadUrl("https://www.americangirl.com/shop/c/clothing")
            R.id.bottomAccessories -> loadUrl("https://www.americangirl.com/shop/c/furniture-accessories")
            R.id.bottomBooks -> loadUrl("https://www.americangirl.com/shop/c/bookstore")
            R.id.bottomAbout -> mWebView.loadUrl("file:///android_res/raw/about.html")
        }
        return true
    }

    private fun loadUrl(url: String) {
        if (!DetectConnection.checkInternetConnection(this)) {
            mWebView.loadUrl("file:///android_res/raw/noconnection.html")
        } else {
            mWebView.loadUrl(url)
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            progressBar.progress = newProgress
        }
    }

    inner class MyWebClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.progress = 0
            progressBar.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let { view?.loadUrl(it) }
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.progress = 100
            progressBar.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack()
        } else {
            moveTaskToBack(true)
            finish()
        }
    }
}

