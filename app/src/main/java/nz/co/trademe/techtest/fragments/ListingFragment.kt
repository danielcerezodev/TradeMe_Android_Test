package nz.co.trademe.techtest.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_listing.view.*
import nz.co.trademe.techtest.R
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.ListingDetailed
import nz.co.trademe.wrapper.models.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A [Fragment] class that displays the listing item details.
 */
class ListingFragment : Fragment(), Callback<ListingDetailed> {

    private lateinit var listingImages: ArrayList<Photo>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get the arguments from the caller
        val listingId = arguments?.getLong("listingId")

        // Start the listing server for retrieving closing soon listings
        TradeMeApi.listingService.retrieveListingDetails(listingId?.toString()).enqueue(this)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setting up the toolbar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            // Send the user back to the closing soon items grid view
            fragmentManager!!.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                    .remove(this)
                    .commit()
        }
    }

    override fun onFailure(call: Call<ListingDetailed>, t: Throwable) {
        Toast.makeText(context, "Error loading details for listing.", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<ListingDetailed>, response: Response<ListingDetailed>) {
        // Make sure that the body of the response is not null
        when (val body = response.body()) {
            null -> Toast.makeText(context, "Error loading details for listing.", Toast.LENGTH_SHORT).show()
            else -> setListingDetails(body)
        }
    }

    /**
     * A function that loads all of the listing details into the view
     */
    private fun setListingDetails(listing: ListingDetailed) {
        // Getting placeholder text
        val memberFeedbackText = view!!.context.getString(R.string.member_feedback)

        // Listing details view items
        val listingImageCarousel = activity?.findViewById(R.id.detailed_listing_images) as CarouselView
        val listingTitle: TextView = view!!.detailed_listing_title
        val listingAskingPrice: TextView = view!!.detailed_listing_starting_price
        // Member details view items
        val listingMemberNickname: TextView = view!!.member_nickname
        val listingMemberLocation: TextView = view!!.member_location
        val listingMemberFeedback: TextView = view!!.member_feedback

        val listingMemberLocationString = listing.member!!.Suburb + ", " + listing.member!!.Region
//        val listingMemberLocationFeedback = listing.member!!.UniquePositive.toString() + ", " + listing.member!!.UniqueNegative.toString()
        val positiveFeedback = listing.member!!.UniquePositive.toString()
        val negativeFeedback = listing.member!!.UniqueNegative.toString()
        val listingMemberLocationFeedback =  memberFeedbackText.format(positiveFeedback, negativeFeedback)

        // Listing data
        listingImages = listing.photos as ArrayList<Photo>
        listingTitle.text = listing.title
        listingAskingPrice.text = listing.startPrice.toString()
        // Member data
        listingMemberNickname.text = listing.member!!.Nickname
        listingMemberLocation.text = listingMemberLocationString
        listingMemberFeedback.text = listingMemberLocationFeedback

        // Image carousel initialise
        listingImageCarousel.setImageListener(imageListener)
        listingImageCarousel.pageCount = listingImages.size
    }

    /**
     * Image Listener for [CarouselView]
     * Loads all the listing images through Glide
     */
    private var imageListener: ImageListener = ImageListener {
        position, imageView ->
        // Setting default properties in case the image can't be displayed
        val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        // Glide request for getting the image via URL
        Glide.with(view!!.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(listingImages[position].Value!!.Gallery)
                .into(imageView)
    }
}