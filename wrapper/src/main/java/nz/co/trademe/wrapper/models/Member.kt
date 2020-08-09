package nz.co.trademe.wrapper.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Member(
        var MemberId: Int?,
        var Nickname: String?,
        var UniqueNegative: Int?,
        var UniquePositive: Int?,
        var FeedbackCount: Int?,
        var Suburb: String?,
        var Region: String?
)