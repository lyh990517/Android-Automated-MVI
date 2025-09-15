package com.yunho.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yunho.data.database.converter.TypeConverter
import com.yunho.data.database.dao.TestDao
import com.yunho.data.database.entity.TestEntity
import javax.inject.Singleton

@Database(
    entities = [TestEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
@Singleton
internal abstract class TestDatabase : RoomDatabase() {
    abstract fun getDao(): TestDao

    companion object {
        operator fun invoke(context: Context): TestDatabase {
            return Room.databaseBuilder(
                context,
                TestDatabase::class.java,
                "test.db"
            ).build()
        }
    }
}
