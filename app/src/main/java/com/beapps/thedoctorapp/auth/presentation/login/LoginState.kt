package com.beapps.thedoctorapp.auth.presentation.login

import com.beapps.thedoctorapp.core.domain.Doctor

data class LoginState(
    val email: String = "",
    val password: String = "",
    val screenState: LoginScreenState = LoginScreenState.Idle,
    val isLoading : Boolean = false
)


sealed interface LoginScreenState {
    data object Idle : LoginScreenState
    data object GoToRegister : LoginScreenState
    data class Success(val data: Doctor) : LoginScreenState
    data class Error(val error: com.beapps.thedoctorapp.core.domain.Error.AuthError.LoginError) : LoginScreenState

}