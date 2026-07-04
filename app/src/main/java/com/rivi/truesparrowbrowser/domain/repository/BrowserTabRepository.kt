package com.rivi.truesparrowbrowser.domain.repository

import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.domain.models.RestoredTabs


interface BrowserTabRepository {
    suspend fun restore(): RestoredTabs?
    suspend fun save(tabs: List<BrowserTab>, activeTabId: String)
}