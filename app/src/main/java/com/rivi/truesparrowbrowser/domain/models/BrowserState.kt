package com.rivi.truesparrowbrowser.domain.models

import com.rivi.truesparrowbrowser.data.models.BrowserTab

data class BrowserState(
    val tabs: List<BrowserTab> = listOf(BrowserTab()),   // always at least 1 tab
    val activeTabId: String = tabs.first().id,
    val isLoading: Boolean = false,

    val loadingProgress: Float = 0f,
    val reload: Boolean = false,
    val stop: Boolean = false,
    val tabCount: Int = 1,

    val history: List<String> = emptyList(),
    val currentIndex: Int = -1,
    val homeTabs: List<Shortcut> = emptyList(),
    val showTabSwitcher: Boolean = false
) {
    val activeTab: BrowserTab = tabs.firstOrNull { it.id == activeTabId } ?: tabs.first()
    val searchQuery: String = activeTab.currentUrl
    val canGoBack: Boolean get() = activeTab.canGoBack
    val canGoForward: Boolean get() = activeTab.canGoForward
}

sealed interface BrowserIntent {
    data object MoveBack : BrowserIntent
    data class SearchAddress(val searchText: String) : BrowserIntent
    data object MoveForward : BrowserIntent
    data object Reload : BrowserIntent
    data object Stop : BrowserIntent
    data object GoHome : BrowserIntent
    data object NewTab : BrowserIntent
    data object OpenTabs : BrowserIntent
    data class UpdateProgress(val progress: Int) : BrowserIntent

    data class SwitchTab(val tabId: String) : BrowserIntent
    data class CloseTab(val tabId: String) : BrowserIntent
    data class UrlChanged(val url: String) : BrowserIntent

}