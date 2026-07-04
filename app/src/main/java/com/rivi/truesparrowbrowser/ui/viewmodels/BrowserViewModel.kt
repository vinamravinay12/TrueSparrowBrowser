package com.rivi.truesparrowbrowser.ui.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.rivi.truesparrowbrowser.core.utils.SearchUtils.toUrlOrSearch
import com.rivi.truesparrowbrowser.domain.models.BrowserIntent
import com.rivi.truesparrowbrowser.domain.models.BrowserState
import com.rivi.truesparrowbrowser.domain.models.Shortcut
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BrowserViewModel : ViewModel() {

    private val _browserState = MutableStateFlow(BrowserState())
    val browserState: StateFlow<BrowserState> = _browserState.asStateFlow()

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
    }


    fun handleIntent(intent: BrowserIntent) {
        when (intent) {
            is BrowserIntent.SearchAddress -> {
                val url = toUrlOrSearch(intent.searchText)
                _browserState.update { state ->
                    if (state.currentIndex >= 0 && state.history[state.currentIndex] == url) {
                        return@update state.copy(searchQuery = url)
                    }
                    val newHistory = state.history.take(state.currentIndex + 1) + url
                    state.copy(
                        searchQuery = url,
                        history = newHistory,
                        currentIndex = newHistory.lastIndex
                    )
                }
            }

            is BrowserIntent.UpdateProgress -> {
                _browserState.update {
                    it.copy(
                        loadingProgress = intent.progress.toFloat(),
                        isLoading = intent.progress in 1..99
                    )
                }
            }

            is BrowserIntent.MoveBack -> {
                _browserState.update { state ->
                    if (!state.canGoBack) return@update state
                    val newIndex = state.currentIndex - 1
                    state.copy(
                        currentIndex = newIndex,
                        searchQuery = state.history[newIndex]
                    )

                }
            }

            is BrowserIntent.MoveForward -> {
                _browserState.update { state ->
                    if (state.canGoForward.not()) return@update state
                    val newIndex = state.currentIndex + 1
                    state.copy(
                        currentIndex = newIndex,
                        searchQuery = state.history[newIndex]
                    )
                }
            }

            is BrowserIntent.GoHome -> {
                _browserState.update { it.copy(searchQuery = "") }
            }

            else -> {}
        }
    }

}