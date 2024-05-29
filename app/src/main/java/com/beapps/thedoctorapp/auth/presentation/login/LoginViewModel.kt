package com.beapps.thedoctorapp.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.auth.domain.AuthManager
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val credentialsManager: AuthCredentialsManager
) : ViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set


    private fun onEmailChanged(value: String): Unit {
        loginState = loginState.copy(email = value)
    }

    private fun onPasswordChanged(value: String): Unit {
        loginState = loginState.copy(password = value)
    }

    private fun onLoginClicked() {
        loginState = loginState.copy(screenState = LoginScreenState.Idle , isLoading = true)
        viewModelScope.launch {
            authManager.login(loginState.email, loginState.password).collect {
                result->
                loginState = when(result) {
                    is Result.Error -> {
                        loginState.copy(isLoading = false , screenState = LoginScreenState.Error(result.error))
                    }

                    is Result.Success -> {
                        saveDoctorCredentials(result.data)
                        loginState.copy(isLoading = false , screenState = LoginScreenState.Success(result.data))
                    }
                }
            }
        }
    }

    private fun onRegisterBtnClicked() {
        loginState = loginState.copy(isLoading = false)
        loginState = loginState.copy(screenState = LoginScreenState.GoToRegister)
    }

    private fun saveDoctorCredentials(doctor: Doctor) {
        credentialsManager.saveDoctorCredentials(doctor)
    }

    private fun onDispose() {
        loginState = loginState.copy(screenState = LoginScreenState.Idle)
    }

    fun onEvent(event: LoginScreenEvents) {
        when (event) {
            is LoginScreenEvents.EmailChanged -> onEmailChanged(event.value)
            is LoginScreenEvents.PasswordChanged -> onPasswordChanged(event.value)
            is LoginScreenEvents.LoginClicked -> onLoginClicked()
            LoginScreenEvents.RegisterClicked -> onRegisterBtnClicked()
            LoginScreenEvents.OnDispose -> onDispose()
        }
    }

}

sealed interface LoginScreenEvents {
    data class EmailChanged(val value: String) : LoginScreenEvents
    data class PasswordChanged(val value: String) : LoginScreenEvents
    data object LoginClicked : LoginScreenEvents

    data object OnDispose : LoginScreenEvents

    data object RegisterClicked : LoginScreenEvents


}