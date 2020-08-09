package nz.co.trademe.wrapper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoValue(
        var FullSize: String?,
        var Gallery: String?,
        var Large: String?,
        var List: String?,
        var Medium: String?,
        var PhotoId: Int?,
        var Thumbnail: String?,
        var OriginalWidth: Int?,
        var OriginalHeight: Int?
)