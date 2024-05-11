package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientFilesViewModel @Inject constructor(
    private val contentManager: ContentManager,
) : ViewModel() {

    var screenState by mutableStateOf(PatientFilesScreenState())
        private set


    private fun getPatientFiles(patient: Patient) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = contentManager.getPatientFiles(patient)
            screenState = when(result) {
                is Result.Error -> {
                    screenState.copy(isLoading = false , error = result.error)
                }

                is Result.Success -> {
                    screenState.copy(isLoading = false , patientFiles = result.data)
                }
            }
        }
    }

    private fun onPatientContentClicked(patientFile : PatientFile) {
        Log.d("ab_do" , "clicked ${patientFile.mimeType}")
    }

    fun onEvent(event: PatientContentScreenEvents) {
        when(event) {
            is PatientContentScreenEvents.GetPatientFiles -> {
                getPatientFiles(event.patient)
            }
            is PatientContentScreenEvents.PatientFileClicked -> onPatientContentClicked(event.patientFile)
        }
    }

    sealed interface PatientContentScreenEvents {
        data class GetPatientFiles(val patient: Patient) :
            PatientContentScreenEvents
        data class PatientFileClicked(val patientFile : PatientFile) :
            PatientContentScreenEvents
    }
}