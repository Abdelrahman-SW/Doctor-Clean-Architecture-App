package com.beapps.thedoctorapp.auth.presentation.register

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
class RegisterViewModel @Inject constructor(
    private val authManager: AuthManager,
    private val credentialsManager: AuthCredentialsManager
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
        registerState = registerState.copy(isLoading = true , screenState = RegisterScreenState.Idle)
        viewModelScope.launch {
            registerState = when (val result = authManager.register(
                doctor = Doctor(
                    name = registerState.name,
                    surname = registerState.surname,
                    phoneNum = registerState.phoneNum,
                    email = registerState.email,
                    password = registerState.password
                )

            )) {
                is Result.Error -> {
                    registerState.copy(isLoading = false , screenState = RegisterScreenState.Error(result.error))
                }

                is Result.Success -> {
                    saveDoctorCredentials(result.data)
                    registerState.copy(isLoading = false , screenState = RegisterScreenState.Success(result.data))
                }
            }
        }
    }

    private fun onLoginClicked() {
        registerState = registerState.copy(isLoading = false , screenState = RegisterScreenState.GoToLogin)
    }

    private fun saveDoctorCredentials(doctor: Doctor) {
        credentialsManager.saveDoctorCredentials(doctor)
    }

    private fun onDispose() {
        registerState = registerState.copy(screenState = RegisterScreenState.Idle)
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
            RegisterScreenEvents.OnDispose -> onDispose()
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
    data object OnDispose : RegisterScreenEvents

}