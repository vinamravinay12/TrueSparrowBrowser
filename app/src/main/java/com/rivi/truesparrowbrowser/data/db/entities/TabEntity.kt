package com.rivi.truesparrowbrowser.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rivi.truesparrowbrowser.data.models.BrowserTab


@Entity(tableName = "tabs")
data class TabEntity(
    @PrimaryKey val id: String,
    val history: List<String>,
    val currentIndex: Int,
    val title: String,
    val position: Int,
    val isActive: Boolean
) {
    fun toBrowserTab() = BrowserTab(
        id = id,
        history = history,
        currentIndex = currentIndex,
        title = title,
    )
}
