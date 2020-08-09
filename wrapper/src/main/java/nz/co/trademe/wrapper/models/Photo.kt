package nz.co.trademe.wrapper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
        var Key: Long?,
        var Value: PhotoValue?
)