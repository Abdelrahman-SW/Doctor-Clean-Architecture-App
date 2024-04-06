package com.beapps.thedoctorapp.auth.presentation.login

data class LoginState(
    var email: String = "",
    var password: String = "",
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var goToRegister : Boolean = false
) {
}