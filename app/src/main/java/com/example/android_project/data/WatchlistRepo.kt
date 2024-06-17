package com.example.android_project.data

import com.example.android_project.models.WatchlistItemDBModel
import com.example.android_project.models.WatchlistItemModel

object WatchlistRepo {
    fun insert(model: WatchlistItemModel, onFinish: () -> Unit) {
        val watchlistItem = WatchlistItemDBModel(
            id = 0,
            name = model.name,
            year = model.year,
            actors = model.actors
        )

        InsertWatchlistItemTask(
            watchlistItem = watchlistItem,
            onFinish = onFinish
        ).execute()
    }

    fun getWatchlist(onFinish: (List<WatchlistItemDBModel>) -> Unit) {
        GetWatchlistTask(onFinish).execute()
    }
}