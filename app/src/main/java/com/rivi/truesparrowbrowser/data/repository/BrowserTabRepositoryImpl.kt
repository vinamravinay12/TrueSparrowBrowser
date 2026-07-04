package com.rivi.truesparrowbrowser.data.repository

import com.rivi.truesparrowbrowser.data.db.dao.TabDao
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.domain.models.RestoredTabs
import com.rivi.truesparrowbrowser.domain.repository.BrowserTabRepository
import javax.inject.Inject

class BrowserTabRepositoryImpl @Inject constructor(private val dao: TabDao) : BrowserTabRepository {

    override suspend fun restore(): RestoredTabs {

    }

    override suspend fun save(tabs: List<BrowserTab>, activeTabId: String) {
        dao.insert(tabs.map { it.toTabEntity() })

    }
}