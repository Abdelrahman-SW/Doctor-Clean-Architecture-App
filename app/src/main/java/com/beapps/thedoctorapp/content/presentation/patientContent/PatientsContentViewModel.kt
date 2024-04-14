package com.beapps.thedoctorapp.content.presentation.patientContent

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsContentViewModel @Inject constructor(
    private val contentManager: ContentManager,
) : ViewModel() {

    var screenState by mutableStateOf(PatientContentScreenState())
        private set


    private fun getPatientContent(doctorId : String , patientFolderName : String) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = contentManager.getPatientContent(doctorId, patientFolderName)
            screenState = when(result) {
                is Result.Error -> {
                    screenState.copy(isLoading = false , error = result.error)
                }

                is Result.Success -> {
                    screenState.copy(isLoading = false , patientsContent = result.data)
                }
            }
        }
    }

    private fun onPatientContentClicked(patientContent : PatientContent) {
        Log.d("ab_do" , "clicked ${patientContent.mimeType}")
    }

    fun onEvent(event: PatientContentScreenEvents) {
        when(event) {
            is PatientContentScreenEvents.GetPatientContent -> {
                getPatientContent(event.doctorId , event.patientFolderName)
            }
            is PatientContentScreenEvents.PatientContentClicked -> onPatientContentClicked(event.patientContent)
        }
    }

    sealed interface PatientContentScreenEvents {
        data class GetPatientContent(val doctorId : String , val patientFolderName : String) : PatientContentScreenEvents
        data class PatientContentClicked(val patientContent : PatientContent) : PatientContentScreenEvents
    }
}