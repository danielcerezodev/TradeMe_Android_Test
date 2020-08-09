package nz.co.trademe.wrapper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listing(
        var ListingId: Long?,
        var Title: String?,
        var Category: String?,
        var StartPrice: Double?,
        var BuyNowPrice: Float?,
        var MaxBidAmount: Float?,
        var PictureHref: String?,
        var Region: String?,
        var Suburb: String?,
        var BidCount: Int?
)