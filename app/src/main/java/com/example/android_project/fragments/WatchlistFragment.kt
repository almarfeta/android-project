package com.example.android_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.adapters.WatchlistListAdapter
import com.example.android_project.models.CartItemModel
import com.example.android_project.models.CategoryModel
import com.example.android_project.models.WatchlistModel

class WatchlistFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_watchlist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }




    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(context)

        val watchlistList = listOf(
            CategoryModel(
                name = "Category name 1",
                description = "Category description 1"
            ),
            WatchlistModel(
                name = "Movie name 1",
                description = "Movie description 1"
            ),
            WatchlistModel(
                name = "Movie name 2",
                description = "Movie description 2"
            ),
            WatchlistModel(
                name = "Movie name 3",
                description = "Movie description 3"
            ),
            WatchlistModel(
                name = "Movie name 4",
                description = "Movie description 4"
            ),
            WatchlistModel(
                name = "Movie name 5",
                description = "Movie description 5"
            ),
        )
        val adapter = WatchlistListAdapter(watchlistList as List<CartItemModel>)

        view?.findViewById<RecyclerView>(R.id.rv_watchlists)?.apply{
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }
}