package com.beapps.thedoctorapp.content.presentation.doctor.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val credentialsManager: AuthCredentialsManager
) : ViewModel() {

    var doctor by mutableStateOf<Doctor?>(null)
        private set

    init {
        doctor = credentialsManager.getCurrentLoggedInDoctor()
    }
    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.Logout -> logout()
        }
    }

    private fun logout() {
        credentialsManager.deleteDoctorCredentials()
    }


}

sealed interface HomeScreenEvents {
    data object Logout : HomeScreenEvents

}

