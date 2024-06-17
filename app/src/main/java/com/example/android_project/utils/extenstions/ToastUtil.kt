package com.example.android_project.utils.extenstions

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.showToast(context: Context?) = context?.let {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.logErrorMessage(tag: String = "AppleTag"){
    Log.e(tag, this)
}