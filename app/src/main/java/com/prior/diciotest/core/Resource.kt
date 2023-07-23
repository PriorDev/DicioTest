package com.prior.diciotest.core

sealed class Resource<T>(val data: T? = null, val message: UiMessages? = null){
    class Success<T>(data: T?): Resource<T>(data = data)
    class Error<T>(message: UiMessages?): Resource<T>(message = message)
    class Loading<T>(val isLoading: Boolean = true): Resource<T>()
}
