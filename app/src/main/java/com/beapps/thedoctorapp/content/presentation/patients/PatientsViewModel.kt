package com.beapps.thedoctorapp.content.presentation.patients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.core.data.sharedPrefs.SharedPrefsAuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsViewModel @Inject constructor(
    private val contentManager: ContentManager,
    credentialsManager: AuthCredentialsManager
) : ViewModel() {

    var screenState by mutableStateOf(PatientScreenState())
        private set
    init {
        credentialsManager.getCurrentLoggedInDoctor()?.let {
            screenState = screenState.copy(doctorId = it.id)
            displayAllPatient(it.id)
        }
    }

    private fun displayAllPatient(doctorId : String) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = contentManager.displayPatients(doctorId)
            screenState = when(result) {
                is Result.Error -> {
                    screenState.copy(isLoading = false , error = result.error)
                }

                is Result.Success -> {
                    screenState.copy(isLoading = false , patients = result.data)
                }
            }
        }
    }

    fun onEvent(event: PatientScreenEvents) {
        when(event) {
            PatientScreenEvents.OnDispose -> {
                screenState = screenState.copy(isLoading = false , error = null)
            }
        }
    }

    sealed interface PatientScreenEvents {
        data object OnDispose : PatientScreenEvents

    }
}