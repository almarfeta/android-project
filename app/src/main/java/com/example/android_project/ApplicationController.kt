package com.example.android_project

import android.app.Application
import androidx.room.Room
import com.example.android_project.data.AppDatabase

class ApplicationController : Application() {

    lateinit var appDatabase: AppDatabase
        private set

    companion object {
        lateinit var instance: ApplicationController
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        setupDatabase()
    }

    private fun setupDatabase() {
        appDatabase = Room.databaseBuilder(
            this, AppDatabase::class.java, "Room_DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}