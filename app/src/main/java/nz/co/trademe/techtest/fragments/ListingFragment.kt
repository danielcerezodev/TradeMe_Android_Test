package nz.co.trademe.techtest.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_listing.view.*
import nz.co.trademe.techtest.R
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.ListingDetailed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [ListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListingFragment : Fragment(), Callback<ListingDetailed> {

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
        Toast.makeText(context, "Error loading closing soon listings.", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<ListingDetailed>, response: Response<ListingDetailed>) {
        // Make sure that the body of the response is not null
        when (val body = response.body()) {
            null -> println("TODO: SHOW ERROR LAYOUT")
            else -> setListingDetails(body)
        }

//        println("REFREEEESH")
//        val ft = fragmentManager!!.beginTransaction()
//        if (Build.VERSION.SDK_INT >= 26) {
//            ft.setReorderingAllowed(false)
//        }
//        ft.detach(this).attach(this).commit()
//        // Reload current fragment
//        var frg: Fragment? = null
//        frg = fragmentManager!!.findFragmentByTag("Your_Fragment_TAG")
//        val ft: FragmentTransaction = fragmentManager!!.beginTransaction()
//        ft.detach(frg!!)
//        ft.attach(frg)
//        ft.commit()
//        // Refresh the fragment view
//        fragmentManager!!.beginTransaction()
//                .commit()
    }

    private fun setListingDetails(listing: ListingDetailed){
        // Listing details view items
        val listingImage: ImageView = view!!.detailed_listing_image
        val listingTitle: TextView = view!!.detailed_listing_title
        val listingAskingPrice: TextView = view!!.detailed_listing_asking_price
        // Member details view items
        val listingMemberNickname: TextView = view!!.member_nickname
        val listingMemberLocation: TextView = view!!.member_location
        val listingMemberFeedback: TextView = view!!.member_feedback

        //
        val listingMemberLocationString = listing.member!!.Suburb + ", " + listing.member!!.Region
        val listingMemberLocationFeedback = listing.member!!.UniquePositive.toString() + ", " + listing.member!!.UniqueNegative.toString()

        // Listing data
        listingTitle.text = listing.title
        listingAskingPrice.text = listing.startPrice.toString()
        // Member data
        listingMemberNickname.text = listing.member!!.Nickname
        listingMemberLocation.text = listingMemberLocationString
        listingMemberFeedback.text = listingMemberLocationFeedback

//        val listingPhoto = listing.photo[0].Value?.Gallery
        println(listing)

        // Setting default properties in case the image can't be displayed
        val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        // Glide request for getting the image via URL
//        Glide.with(view!!.context)
//                .applyDefaultRequestOptions(requestOptions)
//                .load(listingPhoto)
//                .into(listingImage)
    }
}