package com.krshashi.imageoftheday.di

import com.krshashi.imageoftheday.data.repository.NasaImageRepository
import com.krshashi.imageoftheday.domain.repository.ImageRepository
import com.krshashi.imageoftheday.network.NetworkDataSource
import com.krshashi.imageoftheday.network.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun RetrofitNetwork.bindNetworkDataSource(): NetworkDataSource

    @Binds
    fun NasaImageRepository.bindImageRepository(): ImageRepository

}