package com.example.android_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.models.WatchlistModel

class WatchlistListAdapter(
    private val watchlistsList: List<WatchlistModel>
): RecyclerView.Adapter<WatchlistListAdapter.WatchlistViewHolder>() {

    override fun getItemCount() = watchlistsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_watchlist, parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        watchlistsList.getOrNull(position)?.let{watchlist ->
            holder.bind(watchlist)
        }
    }

    inner class WatchlistViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(model: WatchlistModel){

        }
    }
}