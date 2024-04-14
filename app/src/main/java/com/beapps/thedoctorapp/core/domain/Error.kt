package com.beapps.thedoctorapp.core.domain


sealed interface Error {
    sealed interface AuthError : Error {

        sealed interface LoginError : AuthError {
            data object IncorrectEmail : LoginError
            data object IncorrectPassword : LoginError
            data class Others (val message: String?) : LoginError
        }

        sealed interface RegisterError : AuthError {
            data object UserAlreadyExitsError : RegisterError
            data class Others (val message: String?) : RegisterError
        }

    }

    sealed interface GetContentErrors : Error{
        data object EmptyContent : GetContentErrors

        data class Others (val message: String?) : GetContentErrors

    }
}