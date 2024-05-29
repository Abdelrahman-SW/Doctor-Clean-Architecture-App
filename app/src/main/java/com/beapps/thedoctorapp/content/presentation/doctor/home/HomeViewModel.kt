package com.beapps.thedoctorapp.content.presentation.doctor.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.beapps.thedoctorapp.core.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val credentialsManager: AuthCredentialsManager
) : ViewModel() {

    private var shouldApplyRsync = true

    var doctor by mutableStateOf<Doctor?>(null)
        private set

    init {
        doctor = credentialsManager.getCurrentLoggedInDoctor()
    }

    private fun logout() {
        credentialsManager.deleteDoctorCredentials()
        shouldApplyRsync = true
    }

    private fun applyRsync(rsync: () -> Unit) {
        if (shouldApplyRsync) {
            rsync()
            shouldApplyRsync = false
        }
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            HomeScreenEvents.Logout -> logout()
            is HomeScreenEvents.ApplyRsync -> applyRsync(event.rsync)
        }


    }

    sealed interface HomeScreenEvents {
        data object Logout : HomeScreenEvents

        data class ApplyRsync(val rsync: () -> Unit) : HomeScreenEvents

    }
}


