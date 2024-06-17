package com.example.android_project.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_project.models.WatchlistItemDBModel

@Database(
    entities = [
        WatchlistItemDBModel::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val watchlistDao: WatchlistDAO
}