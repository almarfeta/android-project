package com.example.android_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.models.MovieModel

class MovieAdapter(
    private val movieList: List<MovieModel>,
    private val listener: OnAddToWatchlistClickListener
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface OnAddToWatchlistClickListener {
        fun onAddToWatchlist(movie: MovieModel)
    }

    override fun getItemCount() = movieList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        movieList.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    inner class MovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        val movieNameTextView: TextView
        val movieYearTextView: TextView
        val movieActorsTextView: TextView
        val addToWatchlistButton: Button

        init {
            movieNameTextView = view.findViewById(R.id.tv_movie_name)
            movieYearTextView = view.findViewById(R.id.tv_movie_year)
            movieActorsTextView = view.findViewById(R.id.tv_movie_actors)
            addToWatchlistButton = view.findViewById(R.id.add_movie_btn)
        }

        fun bind(model: MovieModel) {
            movieNameTextView.text = model.name
            movieYearTextView.text = model.year
            movieActorsTextView.text = model.actors

            addToWatchlistButton.setOnClickListener {
                listener.onAddToWatchlist(model)
            }
        }
    }
}