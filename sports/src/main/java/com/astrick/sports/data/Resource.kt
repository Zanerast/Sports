package com.astrick.sports.data

sealed class Resource<out T : Any?> {
    object Loading : Resource<Nothing>()
    object Error : Resource<Nothing>()
    data class Ready<out T : Any?>(val content: T) : Resource<T>()
}
