package com.rivi.truesparrowbrowser.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rivi.truesparrowbrowser.core.Converters
import com.rivi.truesparrowbrowser.data.db.dao.TabDao
import com.rivi.truesparrowbrowser.data.db.entities.TabEntity

@Database(entities = [TabEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tabDao(): TabDao


}