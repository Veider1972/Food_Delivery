package ru.veider.core.datatype

sealed class Transport<T> {
    data class Success<T>(val data: T) : Transport<T>()
    data class Error<T>(val error: Throwable) : Transport<T>()
}


