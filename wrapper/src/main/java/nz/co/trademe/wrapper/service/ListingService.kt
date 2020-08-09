package nz.co.trademe.wrapper.service

import nz.co.trademe.wrapper.dto.ClosingSoonListings
import nz.co.trademe.wrapper.dto.ListingDetailed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ListingService {

    @GET("v1/Listings/closing.json")
    fun retrieveClosingSoonListings(): Call<ClosingSoonListings>

    @GET("v1/Listings/{listingId}.json")
    fun retrieveListingDetails(@Path("listingId") listingId: String?): Call<ListingDetailed>
}
