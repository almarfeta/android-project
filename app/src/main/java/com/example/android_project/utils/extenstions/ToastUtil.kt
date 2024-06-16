package com.example.android_project.utils.extenstions

import android.content.Context
import android.widget.Toast

fun String.showToast(context: Context?) = context?.let {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}