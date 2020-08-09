package nz.co.trademe.techtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.trademe.techtest.adapters.ListingsAdapter
import nz.co.trademe.techtest.ui.TopSpacingItemDecoration
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.ClosingSoonListings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * [MainActivity] will display all of the [ClosingSoonListings]
 * This is achieved by an API call through [TradeMeApi]
 * These listings are displayed through a Recycler View [ListingsAdapter]
 */
class MainActivity : AppCompatActivity(), Callback<ClosingSoonListings> {

    private lateinit var listingsAdapter: ListingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This will hide the top bar from the activity
        hideTopBar()

        // Initialise the recycler view
        initRecyclerView()

        // Start the listing server for retrieving closing soon listings
        TradeMeApi.listingService.retrieveClosingSoonListings().enqueue(this)
    }

    /**
     * A function that handles the if API call [ClosingSoonListings] fails
     */
    override fun onFailure(call: Call<ClosingSoonListings>, t: Throwable) {
        Toast.makeText(this, "Error loading closing soon listings.", Toast.LENGTH_SHORT).show()
    }

    /**
     * A function that handles the response from the [ClosingSoonListings] API call
     */
    override fun onResponse(call: Call<ClosingSoonListings>, response: Response<ClosingSoonListings>) {
        // Make sure that the body of the response is not null
        when (val body = response.body()) {
            null -> Toast.makeText(this, "Error loading closing soon listings.", Toast.LENGTH_SHORT).show()
            else -> listingsAdapter.submitList(ArrayList(body.list))
        }
    }

    /**
     * A function that initialises the recycler view by
     * This functions attaches the adapter and the padding between items
     */
    private fun initRecyclerView() {
        recycler_view.apply {
            listingsAdapter = ListingsAdapter()
            val topSpacingDecorator = TopSpacingItemDecoration(resources.getInteger(R.integer.padding_between_listings))
            addItemDecoration(topSpacingDecorator)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listingsAdapter
        }
    }

    /**
     * A function that hides to Top Bar of the activity
     */
    private fun hideTopBar() {
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
    }
}
