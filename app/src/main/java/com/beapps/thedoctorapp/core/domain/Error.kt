package com.beapps.thedoctorapp.core.domain


sealed interface Error {
    sealed interface AuthError : Error {

        sealed interface LoginError : AuthError {
            data object IncorrectEmail : LoginError
            data object IncorrectPassword : LoginError
            data class UndefinedLoginError (val message: String) : LoginError
        }

        sealed interface RegisterError : AuthError {
            data object UserAlreadyExitsError : RegisterError
            data class UndefinedRegisterError (val message: String) : RegisterError
        }

    }

    sealed interface GetDoctorContentErrors : Error{

    }
}