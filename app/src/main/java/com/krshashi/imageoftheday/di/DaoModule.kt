package com.krshashi.imageoftheday.di

import com.krshashi.imageoftheday.data.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DaoModule {

    @Provides
    fun provideImageItemDao(database: Database) = database.imageItemDao()

}