package com.beapps.thedoctorapp.core.domain

sealed interface Error {
    sealed class AuthError : Error {
        data object IncorrectEmail : AuthError()
        data object IncorrectPassword : AuthError()
        data object UserAlreadyExitsError : AuthError()
        data class UndefinedError (val message: String) : AuthError()
    }
}