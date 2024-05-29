package com.beapps.thedoctorapp.auth.presentation.register

import com.beapps.thedoctorapp.core.domain.Doctor


data class RegisterState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val surname: String = "",
    val phoneNum: String = "",
    val isLoading : Boolean = false ,
    val screenState: RegisterScreenState = RegisterScreenState.Idle
)


sealed interface RegisterScreenState {

    data object Idle : RegisterScreenState
    data object GoToLogin : RegisterScreenState
    data class Success(val data: Doctor) : RegisterScreenState
    data class Error(val error: com.beapps.thedoctorapp.core.domain.Error.AuthError.RegisterError) : RegisterScreenState

}
