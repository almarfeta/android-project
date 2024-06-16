package com.example.android_project.models

data class WatchlistModel(
    val name: String,
    val description: String
): CartItemModel(
    type = CartItemType.WATCHLIST
)