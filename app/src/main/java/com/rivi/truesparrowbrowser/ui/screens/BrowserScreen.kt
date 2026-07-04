package com.rivi.truesparrowbrowser.ui.screens

import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.domain.models.BrowserIntent
import com.rivi.truesparrowbrowser.ui.components.BrowserBottomBar
import com.rivi.truesparrowbrowser.ui.components.HomeScreen
import com.rivi.truesparrowbrowser.ui.components.LinearProgressBar
import com.rivi.truesparrowbrowser.ui.components.SearchBarCard
import com.rivi.truesparrowbrowser.ui.viewmodels.BrowserViewModel


@Composable
fun BrowserScreen(viewModel: BrowserViewModel) {
    val state = viewModel.browserState.collectAsState()
    var webView by remember { mutableStateOf<WebView?>(null) }
    Scaffold(bottomBar = {
        BrowserBottomBar(
            tabCount = state.value.tabs.size,
            onHomeClick = { viewModel.handleIntent(BrowserIntent.GoHome) },
            onNewTabClick = { viewModel.handleIntent(BrowserIntent.NewTab) },
            onSettingsClick = { },
            onTabsClick = {
                webView?.let { viewModel.captureThumbnail(state.value.activeTabId, it) }
                viewModel.handleIntent(BrowserIntent.OpenTabs)
            }
        )
    }) { innerPadding ->
        if (state.value.showTabSwitcher) {
            TabsScreen(
                tabs = state.value.tabs,
                activeTabId = state.value.activeTabId,
                onSelectTab = { viewModel.handleIntent(BrowserIntent.SwitchTab(it)) },
                onCloseTab = { viewModel.handleIntent(BrowserIntent.CloseTab(it)) },
                onNewTab = { viewModel.handleIntent(BrowserIntent.NewTab) },
                modifier = Modifier.padding(innerPadding),
                thumbnails = viewModel.thumbnails
            )
        } else {
            BrowserContent(
                viewModel = viewModel,
                onWebViewCreated = { webView = it },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun BrowserContent(
    modifier: Modifier,
    viewModel: BrowserViewModel,
    onWebViewCreated: (WebView) -> Unit = {}
) {
    val state by viewModel.browserState.collectAsState()
    var searchValue by rememberSaveable { mutableStateOf("") }
    var webView by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(state.activeTabId, state.searchQuery) {
        searchValue = state.searchQuery
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        Text(
            text = "True Sparrow",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        SearchBarCard(
            searchValue = searchValue,
            onSearchValueChange = { searchValue = it },
            canGoBack = state.canGoBack,
            canGoForward = state.canGoForward,
            hasUrl = state.searchQuery.isNotBlank(),
            isLoading = state.isLoading,
            moveBack = { viewModel.handleIntent(BrowserIntent.MoveBack) },
            moveForward = { viewModel.handleIntent(BrowserIntent.MoveForward) },
            onSearch = { viewModel.handleIntent(BrowserIntent.SearchAddress(it)) },
            onReload = { webView?.reload() },
            onStop = {
                webView?.stopLoading()
                viewModel.handleIntent(BrowserIntent.Stop)
            }
        )

        LinearProgressBar(loading = state.isLoading, currentProgress = state.loadingProgress)

        Spacer(Modifier.height(16.dp))

        if (state.activeTab.currentUrl.isBlank()) {
            HomeScreen(
                modifier = Modifier.weight(1f),
                onSearch = { viewModel.handleIntent(BrowserIntent.SearchAddress(it)) },
                shortcuts = state.homeTabs
            )
        } else {
            WebViewScreen(
                pageUrl = state.searchQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onProgressChanged = { viewModel.handleIntent(BrowserIntent.UpdateProgress(it)) },
                onUrlChanged = { viewModel.handleIntent(BrowserIntent.UrlChanged(it)) },
                onCreated = {
                    webView = it
                    onWebViewCreated(it)
                },
                onError = { viewModel.handleIntent(BrowserIntent.PageError) },
                onPageStarted = { viewModel.handleIntent(BrowserIntent.ClearError) }
            )
            if (state.isPageError) {
                ErrorScreen(onRetry = { webView?.reload() })
            }
        }


    }

}