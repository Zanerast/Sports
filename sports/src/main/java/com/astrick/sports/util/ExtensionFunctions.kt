package com.astrick.sports.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

internal inline fun <reified T : Parcelable> Bundle.getParcelableWithClass(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelable(key, T::class.java)
    } else {
        this.getParcelable(key)
    }
}

internal inline fun <reified T : Parcelable> Bundle.getParcelableArrayListWithClass(key: String): ArrayList<T> {
    val javaList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getParcelableArrayList(key, T::class.java)
    } else {
        this.getParcelableArrayList(key)
    }
    return ArrayList(javaList ?: arrayListOf())
}
