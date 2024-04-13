package com.beapps.thedoctorapp.content.presentation.home

import androidx.lifecycle.ViewModel
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val credentialsManager: AuthCredentialsManager
) : ViewModel() {

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

