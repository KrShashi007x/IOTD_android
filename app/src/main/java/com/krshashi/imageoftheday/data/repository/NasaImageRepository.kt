package com.krshashi.imageoftheday.data.repository

import com.krshashi.imageoftheday.data.dao.ImageItemDao
import com.krshashi.imageoftheday.data.model.toExternalModel
import com.krshashi.imageoftheday.domain.model.ImageItem
import com.krshashi.imageoftheday.domain.repository.ImageRepository
import com.krshashi.imageoftheday.network.NetworkDataSource
import com.krshashi.imageoftheday.network.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NasaImageRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val imageItemDao: ImageItemDao
) : ImageRepository {

    override val observeDailyImage: Flow<ImageItem?>
        get() = imageItemDao.read().map { it?.toExternalModel() }

    override suspend fun refreshDailyImage(): ResponseState<Unit> {
        return try {
            val result = networkDataSource.getDailyImage(
                resultCount = 1,
                reqThumbnail = true
            )

            if (result.isNotEmpty()) {
                imageItemDao.refresh(result[0])
                ResponseState.Success(Unit)
            } else {
                ResponseState.Failure("Error: Contains no items in response")
            }
        } catch (e: Exception) {
            ResponseState.Failure(e.message)
        }
    }
}