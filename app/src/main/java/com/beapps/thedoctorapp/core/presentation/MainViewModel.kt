package com.beapps.thedoctorapp.core.presentation

import androidx.lifecycle.ViewModel
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val credentialsManager: AuthCredentialsManager
) : ViewModel() {
    fun getTheCurrentRoute (): String {
        val currentLoggedInDoctor = credentialsManager.getCurrentLoggedInDoctor()
        return if(currentLoggedInDoctor == null) Screen.LoginScreen.route
        else Screen.HomeScreen.route
    }

    fun getCurrentLoggedInDoctor(): Doctor? {
        return credentialsManager.getCurrentLoggedInDoctor()
    }
}