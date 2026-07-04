package com.rivi.truesparrowbrowser.data.repository

import com.rivi.truesparrowbrowser.data.db.dao.TabDao
import com.rivi.truesparrowbrowser.data.models.BrowserTab
import com.rivi.truesparrowbrowser.domain.models.RestoredTabs
import com.rivi.truesparrowbrowser.domain.repository.BrowserTabRepository
import javax.inject.Inject

/**
 * Implementation of [BrowserTabRepository] that handles the persistence and retrieval of browser tabs
 * using a local database via [TabDao].
 *
 * This repository acts as the data source for managing the state of multiple browser tabs,
 * including their content, order, and which tab is currently active.
 *
 * @property dao The Data Access Object used for tab database operations.
 */
class BrowserTabRepositoryImpl @Inject constructor(private val dao: TabDao) : BrowserTabRepository {

    override suspend fun restore(): RestoredTabs? {
        val entities = dao.getAllTabs()
        if (entities.isEmpty()) return null
        val tabs = entities.map { it.toBrowserTab() }
        val activeTabId = entities.first { it.isActive }.id
        return RestoredTabs(tabs, activeTabId)
    }

    override suspend fun save(tabs: List<BrowserTab>, activeTabId: String) {
        dao.replaceAll(
            tabs.mapIndexed { index, tab ->
                tab.toEntity(position = index, isActive = tab.id == activeTabId)
            }
        )

    }
}