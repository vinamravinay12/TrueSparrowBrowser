package com.rivi.truesparrowbrowser.ui.viewmodels

import android.graphics.Bitmap
import android.webkit.WebView
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rivi.truesparrowbrowser.core.utils.SearchUtils.toUrlOrSearch
import com.rivi.truesparrowbrowser.core.utils.captureThumbnail
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.domain.models.BrowserIntent
import com.rivi.truesparrowbrowser.domain.models.BrowserState
import com.rivi.truesparrowbrowser.domain.models.Shortcut
import com.rivi.truesparrowbrowser.domain.repository.BrowserTabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(private val repository: BrowserTabRepository) :
    ViewModel() {

    private val _browserState = MutableStateFlow(BrowserState())
    val browserState: StateFlow<BrowserState> = _browserState.asStateFlow()
    val thumbnails = mutableStateMapOf<String, Bitmap>()

    private val defaultShortcuts = listOf(
        Shortcut(
            "Wikipedia",
            "en.wikipedia.org",
            "https://en.wikipedia.org",
            "W",
            Color(0xFF111111)
        ),
        Shortcut(
            "Daily News",
            "news.example.com",
            "https://news.google.com",
            "N",
            Color(0xFFE53935)
        ),
        Shortcut("Kotlin Docs", "kotlinlang.org", "https://kotlinlang.org", "K", Color(0xFF7F52FF))
    )

    init {
        _browserState.update { it.copy(homeTabs = defaultShortcuts) }
        restoreTabs()
    }

    private fun restoreTabs() {
        viewModelScope.launch {
            val restored = repository.restore()
            if (restored != null) {
                _browserState.update {
                    it.copy(tabs = restored.tabs, activeTabId = restored.activeTabId)
                }
            } else persist()
        }
    }

    private fun persist() {
        val state = _browserState.value
        viewModelScope.launch {
            repository.save(state.tabs, state.activeTabId)
        }
    }


    private fun updateActiveTab(updateTab: (BrowserTab) -> BrowserTab) {
        _browserState.update { state ->
            state.copy(
                tabs = state.tabs.map { if (it.id == state.activeTabId) updateTab(it) else it }
            )
        }
    }


    fun handleIntent(intent: BrowserIntent) {
        when (intent) {
            is BrowserIntent.SearchAddress -> {
                val url = toUrlOrSearch(intent.searchText)
                updateActiveTab { tab ->
                    if (tab.currentIndex >= 0 && tab.history[tab.currentIndex] == url) tab
                    else {
                        val newHistory = tab.history.take(tab.currentIndex + 1) + url
                        tab.copy(
                            title = url,
                            history = newHistory,
                            currentIndex = newHistory.lastIndex
                        )
                    }
                }

            }

            is BrowserIntent.MoveBack -> {
                updateActiveTab { tab ->
                    if (tab.canGoBack) tab.copy(currentIndex = tab.currentIndex - 1) else tab
                }
            }

            is BrowserIntent.MoveForward -> {
                updateActiveTab { tab ->
                    if (tab.canGoForward) tab.copy(currentIndex = tab.currentIndex + 1) else tab
                }
            }

            is BrowserIntent.GoHome -> {
                updateActiveTab { tab ->
                    tab.copy(
                        currentIndex = -1
                    )

                }
            }

            is BrowserIntent.NewTab -> {
                val tab = BrowserTab()
                _browserState.update {
                    it.copy(
                        tabs = it.tabs + tab, activeTabId = tab.id,
                        showTabSwitcher = false
                    )
                }
                persist()

            }


            is BrowserIntent.CloseTab -> {
                _browserState.update { state ->
                    val remaining = state.tabs.filterNot { it.id == intent.tabId }
                    val tabs = remaining.ifEmpty { listOf(BrowserTab()) }
                    val activeId =
                        if (state.activeTabId == intent.tabId) tabs.first().id else state.activeTabId
                    state.copy(tabs = tabs, activeTabId = activeId)

                }
                persist()
            }

            is BrowserIntent.SwitchTab -> {
                _browserState.update {
                    it.copy(activeTabId = intent.tabId, showTabSwitcher = false)
                }
                persist()
            }

            is BrowserIntent.UpdateProgress -> {
                _browserState.update {
                    it.copy(
                        loadingProgress = intent.progress.toFloat(),
                        isLoading = intent.progress in 1..99
                    )
                }
            }

            is BrowserIntent.OpenTabs -> _browserState.update {
                it.copy(showTabSwitcher = true)
            }

            is BrowserIntent.UrlChanged -> {
                updateActiveTab { tab ->
                    if (tab.currentIndex >= 0 && tab.history[tab.currentIndex] == intent.url) tab
                    else {
                        val newHistory = tab.history.take(tab.currentIndex + 1) + intent.url
                        tab.copy(
                            history = newHistory,
                            currentIndex = newHistory.lastIndex,
                            title = intent.url
                        )
                    }
                }
                persist()
            }

            is BrowserIntent.Reload -> {

            }

            is BrowserIntent.Stop -> _browserState.update { it.copy(isLoading = false) }

            is BrowserIntent.PageError ->
                _browserState.update { it.copy(isPageError = true, isLoading = false) }

            is BrowserIntent.ClearError ->
                _browserState.update { it.copy(isPageError = false) }

        }
    }

    fun captureThumbnail(tabId: String, webView: WebView) {
        webView.captureThumbnail()?.let { thumbnails[tabId] = it }
    }

}