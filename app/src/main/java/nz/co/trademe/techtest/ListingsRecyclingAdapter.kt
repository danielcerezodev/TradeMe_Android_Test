package nz.co.trademe.techtest

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.listing_item.view.*
import nz.co.trademe.wrapper.dto.Listing

class ListingsRecyclingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Local list of listings
    private var items: List<Listing> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListingsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.listing_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ListingsViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    fun submitList(listingsList: List<Listing>){
        items = listingsList
    }

    // Custom view holder class
    class ListingsViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // listing_item view items
        private val listingImage: ImageView = itemView.listing_image
        private val listingTitle: TextView = itemView.listing_title
        private val listingRegion: TextView = itemView.listing_region
        private val listingAskingPrice: TextView = itemView.listing_asking_price

        // Getting placeholder text
        private val startingBid = itemView.context.getString(R.string.starting_bid)

        fun bind(listing: Listing) {
            // Setting default properties in case the image can't be displayed
            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            // Glide request for getting the image via URL
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(listing.PictureHref)
                    .into(listingImage)

            // Other data
            listingTitle.text = listing.Title
            listingRegion.text = listing.Region
            listingAskingPrice.text = startingBid.format(listing.StartPrice.toString())
        }
    }
}