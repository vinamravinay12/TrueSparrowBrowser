package com.rivi.truesparrowbrowser.ui.screens

import android.webkit.WebView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivi.truesparrowbrowser.R
import com.rivi.truesparrowbrowser.domain.models.BrowserIntent
import com.rivi.truesparrowbrowser.ui.components.BrowserBottomBar
import com.rivi.truesparrowbrowser.ui.components.HomeScreen
import com.rivi.truesparrowbrowser.ui.components.LinearProgressBar
import com.rivi.truesparrowbrowser.ui.viewmodels.BrowserViewModel


@Composable
fun BrowserScreen(viewModel: BrowserViewModel) {
    val state = viewModel.browserState.collectAsState()

    Scaffold(bottomBar = {
        BrowserBottomBar(
            tabCount = state.value.tabs.size,
            onHomeClick = { viewModel.handleIntent(BrowserIntent.GoHome) },
            onNewTabClick = { viewModel.handleIntent(BrowserIntent.NewTab) },
            onSettingsClick = { },
            onTabsClick = { viewModel.handleIntent(BrowserIntent.OpenTabs) }
        )
    }) { innerPadding ->
        if (state.value.showTabSwitcher) {
            TabsScreen(
                tabs = state.value.tabs,
                activeTabId = state.value.activeTabId,
                onSelectTab = { viewModel.handleIntent(BrowserIntent.SwitchTab(it)) },
                onCloseTab = { viewModel.handleIntent(BrowserIntent.CloseTab(it)) },
                onNewTab = { viewModel.handleIntent(BrowserIntent.NewTab) },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            BrowserContent(
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun BrowserContent(
    modifier: Modifier,
    viewModel: BrowserViewModel
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
            text = "True Sparrow", modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, color = Color.Gray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.handleIntent(BrowserIntent.MoveBack) },
                    enabled = state.canGoBack
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        tint = if (state.canGoBack) Color.Black else Color.LightGray,
                        contentDescription = "Move back"
                    )
                }

                IconButton(
                    onClick = { viewModel.handleIntent(BrowserIntent.MoveForward) },
                    enabled = state.canGoForward
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_next),
                        tint = if (state.canGoForward) Color.Black else Color.LightGray,
                        contentDescription = "Move forward"
                    )
                }
                OutlinedTextField(
                    value = searchValue,
                    onValueChange = { searchValue = it },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.9f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { viewModel.handleIntent(BrowserIntent.SearchAddress(searchValue)) }
                    )
                )
                val hasUrl = state.searchQuery.isNotBlank()
                IconButton(onClick = { viewModel.handleIntent(BrowserIntent.Stop) }) {
                    IconButton(
                        onClick = {
                            if (state.isLoading) {
                                webView?.stopLoading()
                                viewModel.handleIntent(BrowserIntent.Stop)
                            } else {
                                webView?.reload()
                            }
                        },
                        enabled = hasUrl
                    ) {
                        Icon(
                            painter = if (state.isLoading) painterResource(R.drawable.ic_stop) else painterResource(
                                R.drawable.ic_reload
                            ),
                            tint = if (hasUrl) Color.Black else Color.LightGray,
                            contentDescription = if (state.isLoading) "Stop" else "Reload"
                        )
                    }

                }

            }
        }

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
                onCreated = { webView = it },
                onError = { viewModel.handleIntent(BrowserIntent.PageError) },
                onPageStarted = { viewModel.handleIntent(BrowserIntent.ClearError) }
            )
            if (state.isPageError) {
                ErrorScreen(onRetry = { webView?.reload() })
            }
        }


    }

}