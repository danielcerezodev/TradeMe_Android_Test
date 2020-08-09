package nz.co.trademe.techtest.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.listing_item.view.*
import nz.co.trademe.techtest.R
import nz.co.trademe.techtest.fragments.ListingFragment
import nz.co.trademe.wrapper.models.Listing

/**
 * A [RecyclerView.Adapter] class that displays all of the closing soon listings
 */
class ListingsAdapter(private val Context: Context) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Listing>() {

        override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem.ListingId == newItem.ListingId
        }

        override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ListingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.listing_item,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListingViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Listing>) {
        differ.submitList(list)
    }

    class ListingViewHolder
    constructor(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        // listing_item view items
        private val listingImage: ImageView = itemView.listing_image
        private val listingTitle: TextView = itemView.listing_title
        private val listingRegion: TextView = itemView.listing_region
        private val listingAskingPrice: TextView = itemView.listing_asking_price

        // Getting placeholder text
        private val startingBid = itemView.context.getString(R.string.starting_bid)

        fun bind(item: Listing) = with(itemView) {
            itemView.setOnClickListener {

                // Fragment for displaying listing details
                val listingFragment = ListingFragment()

                // Passing extra parameters in a bundle
                val bundle = Bundle()
                bundle.putLong("listingId", item.ListingId!!)
                listingFragment.arguments = bundle
                // Call the extension function for fragment transaction
                (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.main_activity, listingFragment)
                        .commit()
            }

            // Listing data
            listingTitle.text = item.Title
            listingRegion.text = item.Region
            listingAskingPrice.text = startingBid.format(item.StartPrice.toString())

            // Setting default properties in case the image can't be displayed
            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            // Glide request for getting the image via URL
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(item.PictureHref)
                    .into(listingImage)
        }
    }
}

