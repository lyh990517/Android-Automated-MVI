package com.yunho.data.database.converter

import androidx.room.TypeConverter
import kotlin.collections.joinToString
import kotlin.text.isEmpty
import kotlin.text.split

internal class TypeConverter {
    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}
