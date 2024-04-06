package com.beapps.thedoctorapp.auth.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.auth.domain.AuthManager
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authManager: AuthManager
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
        loginState = loginState.copy(isLoading = true)
        viewModelScope.launch {
            authManager.login(loginState.email, loginState.password).collect {
                result->
                when(result) {
                    is Result.Error -> {
                        when(result.error) {
                            Error.AuthError.IncorrectEmail -> {
                                Log.d("ab_do" , "IncorrectEmail")
                            }
                            Error.AuthError.IncorrectPassword -> {
                                Log.d("ab_do" , "IncorrectPassword")

                            }
                            is Error.AuthError.UndefinedError -> {
                                Log.d("ab_do" , "UndefinedError ${result.error.message}")
                            }

                            Error.AuthError.UserAlreadyExitsError -> {
                                Log.d("ab_do" , "UserAlreadyExitsError")
                            }
                        }
                        loginState = loginState.copy(isLoading = false)
                    }
                    is Result.Success -> {
                        Log.d("ab_do" , "Success ${result.data?.id}")
                        loginState = loginState.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun onRegisterBtnClicked() {
        loginState = loginState.copy(goToRegister = true, isLoading = false)
    }


    fun onEvent(event: LoginScreenEvents) {
        when (event) {
            is LoginScreenEvents.EmailChanged -> onEmailChanged(event.value)
            is LoginScreenEvents.PasswordChanged -> onPasswordChanged(event.value)
            is LoginScreenEvents.LoginClicked -> onLoginClicked()
            LoginScreenEvents.RegisterClicked -> onRegisterBtnClicked()
        }
    }

}

sealed interface LoginScreenEvents {
    data class EmailChanged(val value: String) : LoginScreenEvents
    data class PasswordChanged(val value: String) : LoginScreenEvents
    data object LoginClicked : LoginScreenEvents

    data object RegisterClicked : LoginScreenEvents
}