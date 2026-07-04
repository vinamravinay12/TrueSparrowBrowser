package com.rivi.truesparrowbrowser.domain.models

import com.rivi.truesparrowbrowser.data.models.BrowserTab

data class RestoredTabs(
    val tabs: List<BrowserTab>,
    val activeTabId: String
)