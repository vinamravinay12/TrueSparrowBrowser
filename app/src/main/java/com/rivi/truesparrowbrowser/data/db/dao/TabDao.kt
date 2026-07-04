package com.rivi.truesparrowbrowser.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rivi.truesparrowbrowser.data.db.entities.TabEntity


@Dao
interface TabDao {

    @Query("SELECT * FROM tabs")
    suspend fun getAllTabs(): List<TabEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tabs: List<TabEntity>)

    @Query("DELETE FROM tabs WHERE id = :tabId")
    suspend fun deleteTab(tabId: String)

    @Query("DELETE FROM tabs")
    suspend fun deleteAllTabs()

    @Transaction
    suspend fun replaceAll(tabs: List<TabEntity>) {
        deleteAllTabs()
        insertAll(tabs)
    }
}