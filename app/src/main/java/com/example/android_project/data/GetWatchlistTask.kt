package com.example.android_project.data

import android.os.AsyncTask
import com.example.android_project.ApplicationController
import com.example.android_project.models.WatchlistItemDBModel

class GetWatchlistTask(
    val onFinish: (List<WatchlistItemDBModel>) -> Unit
) : AsyncTask<Unit, Unit, List<WatchlistItemDBModel>>() {

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Unit?): List<WatchlistItemDBModel> {
        return ApplicationController.instance.appDatabase.watchlistDao.getAll()
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: List<WatchlistItemDBModel>) {
        super.onPostExecute(result)

        onFinish(result)
    }
}