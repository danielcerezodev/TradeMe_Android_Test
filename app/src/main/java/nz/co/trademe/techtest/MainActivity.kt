package nz.co.trademe.techtest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.ClosingSoonListings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<ClosingSoonListings> {

    private lateinit var listingsAdapter: ListingsRecyclingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the listing server for retrieving closing soon listings
        TradeMeApi.listingService.retrieveClosingSoonListings().enqueue(this)
    }

    override fun onFailure(call: Call<ClosingSoonListings>, t: Throwable) {
        Toast.makeText(this, "Error loading closing soon listings.", Toast.LENGTH_SHORT).show()
    }

    override fun onResponse(call: Call<ClosingSoonListings>, response: Response<ClosingSoonListings>) {
        val body = response.body()
        textView.text = when (body) {
            null -> response.message()
            else -> "Closing soon listings total count: ${body.totalCount}"
        }
        // Initialise the recycler view
        initRecyclerView()

        listingsAdapter.submitList(ArrayList(body!!.list))
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            listingsAdapter = ListingsRecyclingAdapter()
            adapter = listingsAdapter
        }
    }
}
