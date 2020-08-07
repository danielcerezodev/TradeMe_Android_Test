package nz.co.trademe.wrapper.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClosingSoonListings(
        @Json(name = "TotalCount") val totalCount: Int,
        @Json(name = "Page") val page: Int = 0,
        @Json(name = "PageSize") val pageSize: Int = 0,
        @Json(name = "List") val list: List<Listing> = listOf<Listing>()
)