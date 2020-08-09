package nz.co.trademe.wrapper.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nz.co.trademe.wrapper.models.Listing

@JsonClass(generateAdapter = true)
data class ClosingSoonListings(
        @Json(name = "TotalCount") val totalCount: Int?,
        @Json(name = "Page") val page: Int?,
        @Json(name = "PageSize") val pageSize: Int?,
        @Json(name = "List") val list: List<Listing> = listOf()
)