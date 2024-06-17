package com.example.android_project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_project.models.WatchlistItemDBModel

@Dao
interface WatchlistDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(watchlistItem: WatchlistItemDBModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(watchlistItem: List<WatchlistItemDBModel>)

    @Query("SELECT * FROM watchlist")
    fun getAll(): List<WatchlistItemDBModel>

}