package com.beapps.thedoctorapp.content.presentation.patinet.patientsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.ContentManager
import com.beapps.thedoctorapp.core.domain.AuthCredentialsManager
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientsListViewModel @Inject constructor(
    private val contentManager: ContentManager,
    credentialsManager: AuthCredentialsManager
) : ViewModel() {

    var screenState by mutableStateOf(PatientsListScreenState())
        private set


    init {
        credentialsManager.getCurrentLoggedInDoctor()?.let {
            displayAllPatient(it.id)
        }
    }

    private fun onSearchQueryChanged(query: String) {
        screenState = screenState.copy(searchQuery = query)
    }

    private fun displayAllPatient(doctorId: String) {
        viewModelScope.launch {
            screenState = screenState.copy(isLoading = true)
            val result = contentManager.getPatients(doctorId)
            screenState = when (result) {
                is Result.Error -> {
                    screenState.copy(isLoading = false, error = result.error)
                }

                is Result.Success -> {
                    screenState.copy(isLoading = false, allPatients = result.data, error = null)
                }
            }

            snapshotFlow {
                screenState.searchQuery
            }.map { query ->
                if (query.isEmpty()) {
                    screenState.allPatients
                } else
                    screenState.allPatients.filter {
                        it.name.contains(query, ignoreCase = true)
                                || it.surname.contains(query, ignoreCase = true)
                    }
            }.collect {
                screenState = screenState.copy(filteredPatients = it)
            }

        }
    }

    fun onEvent(event: PatientScreenEvents) {
        when (event) {
            is PatientScreenEvents.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    sealed interface PatientScreenEvents {
        data class OnSearchQueryChanged(val query: String) : PatientScreenEvents
    }
}