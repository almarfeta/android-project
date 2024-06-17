package com.example.android_project.data

import android.os.AsyncTask
import com.example.android_project.ApplicationController
import com.example.android_project.models.WatchlistItemDBModel

class InsertWatchlistItemTask(
    val watchlistItem: WatchlistItemDBModel,
    val onFinish: () -> Unit
): AsyncTask<Unit, Unit, Unit>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Unit) {
        ApplicationController.instance.appDatabase.watchlistDao.insert(watchlistItem)
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)

        onFinish.invoke()
    }
}