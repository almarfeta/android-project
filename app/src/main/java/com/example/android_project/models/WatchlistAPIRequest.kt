package com.example.android_project.models

data class WatchlistAPIRequest(
    val forename: String = "",
    val surname: String = "",
    val watchlist: List<WatchlistItemModel> = emptyList()
)