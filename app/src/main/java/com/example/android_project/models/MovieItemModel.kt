package com.example.android_project.models

enum class MovieItemType(val key: Int) {
    MOVIE(0),
    MOVIE_CATEGORY(1)
}
sealed class MovieItemModel(
    val type: MovieItemType
)