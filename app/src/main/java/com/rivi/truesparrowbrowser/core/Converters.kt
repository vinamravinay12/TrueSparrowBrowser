package com.rivi.truesparrowbrowser.core

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {

    @TypeConverter
    fun fromList(list: List<String>): String = JSONArray(list).toString()

    @TypeConverter
    fun toList(json: String): List<String> {
        val arr = JSONArray(json)
        return List(arr.length()) { arr.getString(it) }
    }
}