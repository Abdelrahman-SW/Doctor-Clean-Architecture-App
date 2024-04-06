package com.beapps.thedoctorapp.auth.presentation.register

data class RegisterState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var phoneNum: String = "",
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var goToLogin: Boolean = false
) {
}
