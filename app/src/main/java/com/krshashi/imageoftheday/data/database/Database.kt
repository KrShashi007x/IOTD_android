package com.krshashi.imageoftheday.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krshashi.imageoftheday.data.dao.ImageItemDao
import com.krshashi.imageoftheday.data.model.ImageItemEntity

@Database(
    entities = [ImageItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun imageItemDao(): ImageItemDao
}