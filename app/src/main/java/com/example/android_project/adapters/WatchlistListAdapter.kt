package com.example.android_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.models.CartItemModel
import com.example.android_project.models.CartItemType
import com.example.android_project.models.CategoryModel
import com.example.android_project.models.WatchlistModel

class WatchlistListAdapter(
    private val cartItemList: List<CartItemModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = cartItemList.size

    override fun getItemViewType(position: Int) = cartItemList[position].type.key

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        "onCreateViewHolder".logErrorMessage("WatchlistListAdapter")
        val inflater = LayoutInflater.from(parent.context)

        return when(viewType){
            CartItemType.WATCHLIST.key -> {
                val view = inflater.inflate(R.layout.item_watchlist, parent, false)
                return WatchlistViewHolder(view)
            }
            CartItemType.WATCHLISTCATEGORY.key -> {
                val view = inflater.inflate(R.layout.item_watchlist_category, parent, false)
                return WatchlistCategoryViewHolder(view)
            }

            else -> super.createViewHolder(parent, viewType)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        "onBindViewHolder".logErrorMessage("WatchlistListAdapter")


        val model = cartItemList.getOrNull(position)?:return

        when(holder){
            is WatchlistViewHolder->(model as? WatchlistModel)?.let{holder.bind(it)}
            is WatchlistCategoryViewHolder->(model as? CategoryModel)?.let{holder.bind(it)}
        }
    }

    inner class WatchlistViewHolder(val view: View): RecyclerView.ViewHolder(view){

        val watchlistNameTextView: TextView
        val watchlistDescriptionTextView: TextView

        init {
            watchlistNameTextView = view.findViewById(R.id.tv_watchlist_name)
            watchlistDescriptionTextView = view.findViewById(R.id.tv_watchlist_description)
        }

        fun bind(model: WatchlistModel){
        watchlistNameTextView.text = model.name
        watchlistDescriptionTextView.text = model.description
        }
    }
    inner class WatchlistCategoryViewHolder(private val view: View): RecyclerView.ViewHolder(view){

        val categoryNameTextView: TextView
        val categoryDescriptionTextView: TextView

        init {
            categoryNameTextView = view.findViewById(R.id.tv_watchlist_category_name)
            categoryDescriptionTextView = view.findViewById(R.id.tv_watchlist_category_description)
        }

        fun bind(model: CategoryModel){
            categoryNameTextView.text = model.name
            categoryDescriptionTextView.text = model.description
        }
    }
}