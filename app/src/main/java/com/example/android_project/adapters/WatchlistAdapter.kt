package com.example.android_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.models.WatchlistItemModel

class WatchlistAdapter(
    private val watchlistItems: List<WatchlistItemModel>
): RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    override fun getItemCount() = watchlistItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_watchlist, parent, false)
        return WatchlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        watchlistItems.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    inner class WatchlistViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        val watchlistNameTextView: TextView
        val watchlistYearTextView: TextView
        val watchlistActorsTextView: TextView

        init {
            watchlistNameTextView = view.findViewById(R.id.tv_watchlist_name)
            watchlistYearTextView = view.findViewById(R.id.tv_watchlist_year)
            watchlistActorsTextView = view.findViewById(R.id.tv_watchlist_actors)
        }

        fun bind(model: WatchlistItemModel) {
            watchlistNameTextView.text = model.name
            watchlistYearTextView.text = model.year
            watchlistActorsTextView.text = model.actors
        }
    }
}