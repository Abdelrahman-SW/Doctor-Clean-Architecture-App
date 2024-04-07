package com.beapps.thedoctorapp.auth.presentation.login

import com.beapps.thedoctorapp.auth.domain.Doctor

data class LoginState(
    var email: String = "",
    var password: String = "",
    var screenStatue: LoginScreenStatue = LoginScreenStatue.Idle,
    var goToRegister : Boolean = false
)


sealed interface LoginScreenStatue {

    data object Idle : LoginScreenStatue
    data object Loading : LoginScreenStatue
    data class Success(val data: Doctor?) : LoginScreenStatue
    data class Error(val error: com.beapps.thedoctorapp.core.domain.Error.AuthError.LoginError) : LoginScreenStatue

}