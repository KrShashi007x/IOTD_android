package com.krshashi.imageoftheday.domain.model

enum class MediaType(val value: String) {
    VIDEO("video"),
    IMAGE("image")
}

data class ImageItem(
    var date: String? = null,
    var explanation: String? = null,
    var hdurl: String? = null,
    var mediaType: MediaType? = null,
    var serviceVersion: String? = null,
    var title: String? = null,
    var url: String? = null
)
