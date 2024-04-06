package com.beapps.thedoctorapp.auth.presentation.register

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
class RegisterViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    var registerState by mutableStateOf(RegisterState())
        private set


    private fun onEmailChanged(value: String): Unit {
        registerState = registerState.copy(email = value)
    }

    private fun onPasswordChanged(value: String): Unit {
        registerState = registerState.copy(password = value)
    }

    private fun onNameChanged(value: String): Unit {
        registerState = registerState.copy(name = value)
    }


    private fun onSurnameChanged(value: String): Unit {
        registerState = registerState.copy(surname = value)
    }


    private fun onPhoneNumChanged(value: String): Unit {
        registerState = registerState.copy(phoneNum = value)
    }


    private fun onRegisterClicked() {
        registerState = registerState.copy(isLoading = true)
        viewModelScope.launch {
            when(val result = authManager.register(
                registerState.name,
                registerState.surname,
                registerState.phoneNum,
                registerState.email,
                registerState.password)) {
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
                    registerState = registerState.copy(isLoading = false)
                }
                is Result.Success -> {
                    Log.d("ab_do" , "Success ${result.data?.id}")
                    registerState = registerState.copy(isLoading = false)
                }
            }
        }
    }

    private fun onLoginClicked() {
        registerState = registerState.copy(goToLogin = true, isLoading = false)
    }


    fun onEvent(event: RegisterScreenEvents) {
        when (event) {
            is RegisterScreenEvents.EmailChanged -> onEmailChanged(event.value)
            is RegisterScreenEvents.PasswordChanged -> onPasswordChanged(event.value)
            is RegisterScreenEvents.LoginClicked -> onLoginClicked()
            is RegisterScreenEvents.RegisterClicked -> onRegisterClicked()
            is RegisterScreenEvents.NameChanged -> onNameChanged(event.value)
            is RegisterScreenEvents.PhoneNumChanged -> onPhoneNumChanged(event.value)
            is RegisterScreenEvents.SurnameChanged -> onSurnameChanged(event.value)
        }
    }

}

sealed interface RegisterScreenEvents {
    data class EmailChanged(val value: String) : RegisterScreenEvents
    data class PasswordChanged(val value: String) : RegisterScreenEvents
    data class NameChanged(val value: String) : RegisterScreenEvents
    data class SurnameChanged(val value: String) : RegisterScreenEvents
    data class PhoneNumChanged(val value: String) : RegisterScreenEvents
    data object LoginClicked : RegisterScreenEvents
    data object RegisterClicked : RegisterScreenEvents
}