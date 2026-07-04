package com.rivi.truesparrowbrowser.data.models


import com.rivi.truesparrowbrowser.data.db.entities.TabEntity
import java.util.UUID

data class BrowserTab(
    val id: String = UUID.randomUUID().toString(),
    val history: List<String> = emptyList(),
    val currentIndex: Int = -1,
    val title: String = "New Tab"
) {
    val currentUrl: String get() = history.getOrNull(currentIndex) ?: ""
    val canGoBack: Boolean get() = currentIndex > 0
    val canGoForward: Boolean get() = currentIndex < history.size - 1

    fun toEntity(position: Int, isActive: Boolean) = TabEntity(
        id = id,
        history = history,
        currentIndex = currentIndex,
        title = title,
        position = position,
        isActive = isActive
    )
}
