package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.NoteRepo
import com.beapps.thedoctorapp.content.domain.models.Patient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientNotesViewModel @Inject constructor(
    val noteRepo: NoteRepo
) : ViewModel() {

    var state by mutableStateOf(PatientNotesState())
        private set

    fun onAddNoteClicked() {
        state = state.copy(viewState = ViewState.ADD)
    }

    fun changeToViewNotes () {
        state = state.copy(viewState = ViewState.VIEW)
    }
    fun getNotes (patient: Patient, doctorId: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            noteRepo.getPatientNotes(patient, doctorId).collect {
                state = state.copy(notes = it)
            }
            state = state.copy(isLoading = false)
        }
    }
}