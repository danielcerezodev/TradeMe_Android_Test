package nz.co.trademe.wrapper.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import nz.co.trademe.wrapper.models.Member
import nz.co.trademe.wrapper.models.Photo

@JsonClass(generateAdapter = true)
data class ListingDetailed(
        @Json(name = "ListingId") val listingId: Long?,
        @Json(name = "Title") val title: String?,
        @Json(name = "Category") val category: String?,
        @Json(name = "StartPrice") val startPrice: Float?,
        @Json(name = "Member") val member: Member?,
        @Json(name = "Photos") val photos: List<Photo> = listOf()
)