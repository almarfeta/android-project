package com.example.android_project.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("watchlist")
data class WatchlistItemDBModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val year: String,
    val actors: String
)
