package com.example.rssfeed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.rssfeed.databinding.ActivityDetailsBinding

private const val URL_MESSAGE = "com.example.rssfeed.URL";

class DetailsActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progressBar.isVisible = newProgress < 100
                    binding.progressBar.progress = newProgress

                    super.onProgressChanged(view, newProgress)
                }
            }
        }

        val url = intent.getStringExtra(URL_MESSAGE)
        if (url != null) {
            binding.webview.loadUrl(url)
        }
    }

    companion object {
        fun createStartActivityIntent(context: Context, url: String) =
            Intent(context, DetailsActivity::class.java)
                .apply { putExtra(URL_MESSAGE, url) }
    }
}