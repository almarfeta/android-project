package com.example.android_project.models

enum class CartItemType(val key: Int) {
    WATCHLIST(0),
    WATCHLISTCATEGORY(1)
}
sealed class CartItemModel(
    val type: CartItemType
)