package com.example.android_project.utils.extenstions

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.android_project.models.api.WatchlistAPIResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun String.showToast(context: Context?) = context?.let {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.logErrorMessage(tag: String = "AppleTag"){
    Log.e(tag, this)
}

fun handleWatchlistResponse(response: String) {
    val type = object: TypeToken<List<WatchlistAPIResponse>>(){}.type
    val responseJsonArray = Gson().fromJson<List<WatchlistAPIResponse>>(response, type)
}