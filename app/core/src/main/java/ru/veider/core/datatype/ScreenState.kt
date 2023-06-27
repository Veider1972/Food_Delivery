package ru.veider.core.datatype

sealed class ScreenState<T> {
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error<T>(val error: Throwable) : ScreenState<T>()
    class Loading<T> : ScreenState<T>()
}


