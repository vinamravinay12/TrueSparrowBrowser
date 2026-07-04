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

@Composable
fun WebViewScreen(
    pageUrl: String,
    modifier: Modifier,
    onProgressChanged: (Int) -> Unit = {},
    onUrlChanged: (String) -> Unit = {},
    onCreated: (WebView) -> Unit = {},
    onError: () -> Unit = {},
    onPageStarted: () -> Unit = {}
) {
    val context = LocalContext.current
    var lastLoadedUrl by remember { mutableStateOf<String?>(null) }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(false)
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(v: WebView?, u: String?, f: android.graphics.Bitmap?) {
                    super.onPageStarted(v, u, f); onPageStarted()
                }

                override fun doUpdateVisitedHistory(v: WebView, u: String, r: Boolean) {
                    super.doUpdateVisitedHistory(v, u, r)
                    if (u != lastLoadedUrl) {
                        lastLoadedUrl = u; onUrlChanged(u)
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
            if (pageUrl.isNotBlank() && pageUrl != lastLoadedUrl) {
                lastLoadedUrl = pageUrl
                it.loadUrl(pageUrl)
            }
        }
    )
}