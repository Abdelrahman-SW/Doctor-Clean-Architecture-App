package com.beapps.thedoctorapp.auth.presentation.register
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (

): ViewModel() {

    var registerState by mutableStateOf(RegisterState())
        private set


    private fun onEmailChanged (value : String) : Unit {
        registerState = registerState.copy(email = value)
    }

    private fun onPasswordChanged (value : String) : Unit {
        registerState = registerState.copy(password = value)
    }

    private fun onNameChanged (value : String) : Unit {
        registerState = registerState.copy(name = value)
    }


    private fun onSurnameChanged (value : String) : Unit {
        registerState = registerState.copy(surname = value)
    }


    private fun onPhoneNumChanged (value : String) : Unit {
        registerState = registerState.copy(phoneNum = value)
    }


    private fun onRegisterClicked() {
        registerState = registerState.copy(isLoading = true)
    }

    private fun onLoginClicked() {
        registerState = registerState.copy(goToLogin = true, isLoading = false)
    }


    fun onEvent(event : RegisterScreenEvents) {
        when(event) {
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
    data class PasswordChanged(val value: String)  : RegisterScreenEvents
    data class NameChanged(val value: String)  : RegisterScreenEvents
    data class SurnameChanged(val value: String)  : RegisterScreenEvents
    data class PhoneNumChanged(val value: String)  : RegisterScreenEvents
    data object LoginClicked: RegisterScreenEvents
    data object RegisterClicked : RegisterScreenEvents
}