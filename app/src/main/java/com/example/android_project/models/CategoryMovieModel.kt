package com.example.android_project.models

data class CategoryMovieModel (
    val name:String,
    val description: String
): MovieItemModel(
    MovieItemType.MOVIE_CATEGORY
)