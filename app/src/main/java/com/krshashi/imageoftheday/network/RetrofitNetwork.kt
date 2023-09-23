package com.krshashi.imageoftheday.network

import com.krshashi.imageoftheday.data.model.ImageItemEntity
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface NetworkDataSource {
   suspend fun getDailyImage(
        resultCount: Int,
        reqThumbnail: Boolean,
        apiKey: String
    ): List<ImageItemEntity>
}

private interface RetrofitNetworkApi {
    @GET("planetary/apod")
    suspend fun getRequest(
        @Query("count") resultCount: Int,
        @Query("thumbs") reqThumbnail: Boolean,
        @Query("api_key") apiKey: String,
    ): List<ImageItemEntity>
}

@Singleton
class RetrofitNetwork @Inject constructor(
    okhttpCallFactory: Call.Factory
) :NetworkDataSource {
    private val baseUrl: String = "https://api.nasa.gov/"

    private val networkAPI = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getDailyImage(
        resultCount: Int,
        reqThumbnail: Boolean,
        apiKey: String
    ) = networkAPI.getRequest(resultCount, reqThumbnail, apiKey)
}