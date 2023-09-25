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

/**
 * Repository class responsible for providing the data to ui layer (UI + ViewModel)
 * observeDailyImage will show the result from database to UI, if we have then data else null
 * refreshDailyImage will fetch the data from server and cache it offline and room will
 * detect the change and emit the new value that will be observed by observeDailyImage
 *
 * NetworkDataSource interface is used to call network api
 * ImageItemDao is used to get data from room database
 */

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
                imageItemDao.save(result[0])
                ResponseState.Success(Unit)
            } else {
                ResponseState.Failure("NoContentError")
            }
        } catch (e: Exception) {
            ResponseState.Failure(e.message)
        }
    }
}