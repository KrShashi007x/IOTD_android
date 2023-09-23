package com.krshashi.imageoftheday.di

import android.content.Context
import androidx.room.Room
import com.krshashi.imageoftheday.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME: String = "core_db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(appContext, Database::class.java, DB_NAME)
        .build()
}