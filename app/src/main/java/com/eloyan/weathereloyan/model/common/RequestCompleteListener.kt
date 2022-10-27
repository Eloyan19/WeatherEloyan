package com.eloyan.weathereloyan.model.common

import com.eloyan.weathereloyan.model.location.LocationError

interface RequestCompleteListener<T, Y> {
    fun onRequestSuccess(data: T)
    fun onRequestFailed(error: Y)
}

sealed class RequestResult<out A : Any, out B : Any> {
    data class Success<A : Any>(val successResponse: A) : RequestResult<A, Nothing>()
    data class Error<B : Any>(val locationError: LocationError) : RequestResult<Nothing, B>()
}