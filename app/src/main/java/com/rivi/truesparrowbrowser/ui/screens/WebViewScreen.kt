package com.rivi.truesparrowbrowser.ui.screens

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(
    pageUrl: String, modifier: Modifier, onProgressChanged: (Int) -> Unit = {},
    onUrlChanged: (String) -> Unit
) {
    var lastLoadedUrl by rememberSaveable { mutableStateOf<String?>(null) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            return@AndroidView WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun doUpdateVisitedHistory(
                        view: WebView?,
                        url: String?,
                        isReload: Boolean
                    ) {
                        super.doUpdateVisitedHistory(view, url, isReload)
                        if (url != lastLoadedUrl) {
                            lastLoadedUrl = url
                            url?.let { onUrlChanged(it) }
                        }
                    }
                }

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(false)
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        onProgressChanged(newProgress)
                    }
                }
            }
        },
        update = { webView ->
            if (pageUrl.isNotBlank() && pageUrl != lastLoadedUrl) {
                lastLoadedUrl = pageUrl
                webView.loadUrl(pageUrl)
            }
        }

    )
}