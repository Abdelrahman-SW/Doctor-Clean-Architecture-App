package com.beapps.thedoctorapp.core.domain

typealias AppErrors = Error

sealed interface Result<D, E : AppErrors> {
    data class Success<D, E : AppErrors>(val data: D) : Result<D, E>
    data class Error<D, E : AppErrors> (val error : E) : Result<D, E>
}

