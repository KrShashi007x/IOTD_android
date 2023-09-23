package com.krshashi.imageoftheday.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.krshashi.imageoftheday.domain.model.ImageItem
import com.krshashi.imageoftheday.domain.model.MediaType

@Entity(tableName = "imageEntity")
data class ImageItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("explanation")
    var explanation: String? = null,
    @SerializedName("hdurl")
    var hdurl: String? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("service_version")
    var serviceVersion: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("copyright")
    var copyright: String? = null
)

fun ImageItemEntity.toExternalModel() = ImageItem(
    date,
    explanation,
    hdurl,
    getMediaType(mediaType),
    serviceVersion,
    title,
    url
)

fun getMediaType(mediaType: String?): MediaType {
    return if (mediaType == MediaType.IMAGE.value) MediaType.IMAGE
    else MediaType.VIDEO
}