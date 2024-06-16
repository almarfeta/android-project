package com.example.android_project.models

enum class CartItemType(val key: Int) {
    WATCHLIST(0),
    WATCHLIST_CATEGORY(1)
}
sealed class CartItemModel(
    val type: CartItemType
)