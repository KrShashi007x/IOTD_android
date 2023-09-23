package com.krshashi.imageoftheday.domain.repository

import com.krshashi.imageoftheday.domain.model.ImageItem
import com.krshashi.imageoftheday.network.ResponseState
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    val observeDailyImage: Flow<ImageItem?>
    suspend fun refreshDailyImage(): ResponseState<Unit>
}