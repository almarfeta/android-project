package com.example.android_project.models.api

import com.google.gson.annotations.SerializedName

data class WatchlistAPIResponse (
    val id: Int,
    @SerializedName("title")
    val name: String,
    val category: String,
    val description: String
)