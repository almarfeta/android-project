package com.example.android_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.models.MovieItemModel
import com.example.android_project.models.MovieItemType
import com.example.android_project.models.CategoryMovieModel
import com.example.android_project.models.MovieModel

class MovieAdapter(
    private val cartItemList: List<MovieItemModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = cartItemList.size

    override fun getItemViewType(position: Int) = cartItemList[position].type.key

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType){
            MovieItemType.MOVIE.key -> {
                val view = inflater.inflate(R.layout.item_movie, parent, false)
                MovieViewHolder(view)
            }
            MovieItemType.MOVIE_CATEGORY.key -> {
                val view = inflater.inflate(R.layout.item_movie_category, parent, false)
                MovieCategoryViewHolder(view)
            }

            else -> super.createViewHolder(parent, viewType)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = cartItemList.getOrNull(position) ?: return

        when(holder){
            is MovieViewHolder -> (model as? MovieModel)?.let { holder.bind(it) }
            is MovieCategoryViewHolder -> (model as? CategoryMovieModel)?.let { holder.bind(it) }
        }
    }

    inner class MovieViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        val movieNameTextView: TextView
        val movieDescriptionTextView: TextView

        init {
            movieNameTextView = view.findViewById(R.id.tv_movie_name)
            movieDescriptionTextView = view.findViewById(R.id.tv_movie_description)
        }

        fun bind(model: MovieModel) {
            movieNameTextView.text = model.name
            movieDescriptionTextView.text = model.description
        }
    }

    inner class MovieCategoryViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        val categoryNameTextView: TextView
        val categoryDescriptionTextView: TextView

        init {
            categoryNameTextView = view.findViewById(R.id.tv_movie_category_name)
            categoryDescriptionTextView = view.findViewById(R.id.tv_movie_category_description)
        }

        fun bind(model: CategoryMovieModel){
            categoryNameTextView.text = model.name
            categoryDescriptionTextView.text = model.description
        }
    }
}