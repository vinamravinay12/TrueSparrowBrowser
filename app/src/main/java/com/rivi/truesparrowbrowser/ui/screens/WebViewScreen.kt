import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A Composable function that wraps an Android [WebView] to display web content.
 *
 * @param pageUrl The URL to be loaded in the WebView.
 * @param modifier The [Modifier] to be applied to the WebView layout.
 * @param onProgressChanged A callback invoked when the loading progress changes, returning an integer between 0 and 100.
 */
@Composable
fun WebViewScreen(
    pageUrl: String,
    modifier: Modifier,
    onProgressChanged: (Int) -> Unit = {},
    onUrlChanged: (String) -> Unit = {},
    onCreated: (WebView) -> Unit = {},
    onError: () -> Unit = {},
    onPageStarted: () -> Unit = {},
    onNavStateChanged: (canGoBack: Boolean, canGoForward: Boolean) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    var lastLoadedUrl by remember { mutableStateOf<String?>(null) }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(v: WebView?, u: String?, f: android.graphics.Bitmap?) {
                    super.onPageStarted(v, u, f)
                    onPageStarted()
                }

                override fun doUpdateVisitedHistory(view: WebView, url: String, r: Boolean) {
                    super.doUpdateVisitedHistory(view, url, r)
                    if (url != lastLoadedUrl) {
                        lastLoadedUrl = url
                        onUrlChanged(url)
                        onNavStateChanged(view.canGoBack(), view.canGoForward())
                    }
                }

                override fun onReceivedError(
                    v: WebView,
                    req: android.webkit.WebResourceRequest,
                    e: android.webkit.WebResourceError
                ) {
                    super.onReceivedError(v, req, e)
                    if (req.isForMainFrame) onError()
                }
            }
            webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(v: WebView?, p: Int) {
                    onProgressChanged(p)
                }

                override fun onPermissionRequest(request: android.webkit.PermissionRequest) {
                    request.deny()
                }
            }
        }.also { onCreated(it) }
    }

    AndroidView(
        factory = { webView },
        modifier = modifier,
        update = {
            if (pageUrl.isNotBlank() && pageUrl != lastLoadedUrl && pageUrl != webView.url) {
                lastLoadedUrl = pageUrl
                it.loadUrl(pageUrl)
            }
        }
    )
}