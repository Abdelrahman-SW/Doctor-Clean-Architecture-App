package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
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
            val result = contentManager.getPatientFiles(patient , graphsOnly =  screenState.showGraphsOnly)
            screenState = when (result) {
                is Result.Error -> {
                    screenState.copy(isLoading = false, error = result.error)
                }

                is Result.Success -> {
                    screenState.copy(isLoading = false, allPatientFiles = result.data)
                }
            }

            snapshotFlow {
                screenState.searchQuery
            }.map { query ->
                if (query.isEmpty()) {
                    screenState.allPatientFiles
                } else
                    screenState.allPatientFiles.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
            }.collect {
                screenState = screenState.copy(filteredPatientFiles = it)
            }
        }
    }

    private fun onPatientContentClicked(patientFile: PatientFile) {
        Log.d("ab_do", "clicked ${patientFile.mimeType}")
    }

    private fun onSearchQueryChanged(query: String) {
        screenState = screenState.copy(searchQuery = query)
    }

    fun onEvent(event: PatientFilesScreenEvents) {
        when (event) {
            is PatientFilesScreenEvents.GetPatientFiles -> {
                getPatientFiles(event.patient)
            }

            is PatientFilesScreenEvents.PatientFileClicked -> onPatientContentClicked(event.patientFile)
            is PatientFilesScreenEvents.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is PatientFilesScreenEvents.ShowGraphsOnly -> showGraphsOnly(event.showGraphs)
        }
    }

    private fun showGraphsOnly(showGraphs: Boolean) {
        screenState = screenState.copy(showGraphsOnly = showGraphs)
    }

    sealed interface PatientFilesScreenEvents {
        data class GetPatientFiles(val patient: Patient) :
            PatientFilesScreenEvents

        data class PatientFileClicked(val patientFile: PatientFile) :
            PatientFilesScreenEvents

        data class OnSearchQueryChanged(val query: String) : PatientFilesScreenEvents

        data class ShowGraphsOnly (val showGraphs : Boolean) : PatientFilesScreenEvents

    }
}