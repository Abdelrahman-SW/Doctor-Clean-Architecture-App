package com.beapps.thedoctorapp.auth.presentation.register

import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.domain.Error


data class RegisterState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var phoneNum: String = "",
    var goToLogin: Boolean = false,
    var screenStatue: RegisterScreenStatue = RegisterScreenStatue.Idle
)


sealed interface RegisterScreenStatue {

    data object Idle : RegisterScreenStatue
    data object Loading : RegisterScreenStatue
    data class Success(val data: Doctor?) : RegisterScreenStatue
    data class Error(val error: com.beapps.thedoctorapp.core.domain.Error.AuthError.RegisterError) : RegisterScreenStatue

}
