package com.yunho.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "test",
)
internal data class TestEntity(
    @PrimaryKey val id: String,
)
