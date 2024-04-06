package com.beapps.thedoctorapp.auth.presentation.login
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (

): ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set


    private fun onEmailChanged (value : String) : Unit {
        loginState = loginState.copy(email = value)
    }

    private fun onPasswordChanged (value : String) : Unit {
        loginState = loginState.copy(password = value)
    }

    private fun onLoginClicked() {
        loginState = loginState.copy(isLoading = true)
    }

    private fun onRegisterBtnClicked() {
        loginState = loginState.copy(goToRegister = true)
    }


    fun onEvent(event : LoginScreenEvents) {
        when(event) {
            is LoginScreenEvents.EmailChanged -> onEmailChanged(event.value)
            is LoginScreenEvents.PasswordChanged -> onPasswordChanged(event.value)
            is LoginScreenEvents.LoginClicked -> onLoginClicked()
            LoginScreenEvents.RegisterClicked -> onRegisterBtnClicked()
        }
    }

}

sealed interface LoginScreenEvents {
    data class EmailChanged(val value: String) : LoginScreenEvents
    data class PasswordChanged(val value: String)  : LoginScreenEvents
    data object LoginClicked: LoginScreenEvents

    data object RegisterClicked : LoginScreenEvents
}