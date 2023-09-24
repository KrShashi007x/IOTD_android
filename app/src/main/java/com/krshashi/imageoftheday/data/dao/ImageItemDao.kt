package com.krshashi.imageoftheday.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.krshashi.imageoftheday.data.model.ImageItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: ImageItemEntity)

    @Query("SELECT * FROM imageEntity LIMIT 1")
    fun read(): Flow<ImageItemEntity?>

}