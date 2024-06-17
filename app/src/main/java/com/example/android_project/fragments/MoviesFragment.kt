package com.example.android_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.android_project.R
import com.example.android_project.adapters.WatchlistListAdapter
import com.example.android_project.models.CartItemModel
import com.example.android_project.models.CategoryModel
import com.example.android_project.models.WatchlistModel
import com.example.android_project.models.api.WatchlistAPIResponse
import com.example.android_project.utils.extenstions.handleWatchlistResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MoviesFragment: Fragment() {

    private val watchlistItemList by lazy {
        ArrayList<CartItemModel>()
    }
    private val adapter by lazy {
        WatchlistListAdapter(watchlistItemList)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
//        getWatchlist()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(context)


        view?.findViewById<RecyclerView>(R.id.rv_watchlist)?.apply{
            this.layoutManager = layoutManager
            this.adapter = this@MoviesFragment.adapter
        }
        fun getWatchlist(){

            val queue = Volley.newRequestQueue(context)
            val url = "https://www.fakestoreapi.com/products"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    handleWatchlistResponse(response)
                },
                {
                    "That didn't work!"
                })

            queue.add(stringRequest)

        }
        fun handleWatchlistResponse(response: String) {
            val type = object: TypeToken<List<WatchlistAPIResponse>>(){}.type
            val responseJsonArray = Gson().fromJson<List<WatchlistAPIResponse>>(response, type)

            responseJsonArray
                .groupBy{it.category}
                .forEach{//it:Map.Entry<string, List<watchlistAPIResponse>>
                    val categoryModel = CategoryModel(
                        name = it.key,
                        description =  it.key
                    )
                    val watchlist = it.value.map{ watchlistApi ->
                        WatchlistModel(
                            name = watchlistApi.name,
                            description = watchlistApi.description
                        )
                    }
                    watchlistItemList.add(categoryModel)
                    watchlistItemList.addAll(watchlist)
                }
            adapter.notifyItemRangeInserted(0, watchlistItemList.size)
//          adapter.notifyDataSetChanged()
        }


//        val adapter = WatchlistListAdapter(movieList)
//
//        view?.findViewById<RecyclerView>(R.id.rv_watchlist)?.apply{
//            this.layoutManager = layoutManager
//            this.adapter = adapter
//        }
    }
}