package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.NoteRepo
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientNotesViewModel @Inject constructor(
    private val noteRepo: NoteRepo
) : ViewModel() {

    private var getNotesFired = false
    var state by mutableStateOf(PatientNotesState())
        private set

    private fun onAddNoteClicked() {
        state = state.copy(viewState = ViewState.ADD , noteText = "")
    }

    private fun onNoteClicked(note: PatientNote) {
        state = state.copy(viewState = ViewState.EDIT, selectedNote = note)
    }

    private fun changeToViewNotes() {
        state = state.copy(viewState = ViewState.VIEW)
    }

    private fun getNotes(patient: Patient) {
        if (getNotesFired) return
        getNotesFired = true
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            noteRepo.getPatientNotes(patient).collect {
                state = state.copy(notes = it.reversed() , isLoading = false)
            }
        }
    }

    private fun onNoteTextChange(note: String) {
        state = if (state.viewState == ViewState.ADD) {
            state.copy(noteText = note)
        } else {
            state.copy(selectedNote = state.selectedNote!!.copy(note = note))

        }
    }


    fun onEvent(event: PatientNotesEvents) {
        when (event) {
            is PatientNotesEvents.ChangeToViewNotes -> changeToViewNotes()
            is PatientNotesEvents.GetNotes -> getNotes(event.patient)
            is PatientNotesEvents.OnAddNoteClicked -> onAddNoteClicked()
            is PatientNotesEvents.OnNoteClicked -> onNoteClicked(event.note)
            is PatientNotesEvents.UpsertNote -> upsertNote(event.patient)
            is PatientNotesEvents.NoteTextChange -> onNoteTextChange(event.note)
            is PatientNotesEvents.OnNoteDeleteClicked -> deleteNote(event.note)
        }
    }

    private fun deleteNote(note: PatientNote) {
        viewModelScope.launch {
            noteRepo.deleteNote(note)
        }
    }


    private fun upsertNote(patient: Patient?) {
        patient?.let {
            val note = if (state.viewState == ViewState.ADD) {
                PatientNote(
                    note = state.noteText,
                    byDoctorId = patient.assignedDoctorId,
                    toPatientId = patient.id
                )
            }
            else {
                state.selectedNote!!
            }
            viewModelScope.launch {
                if (state.viewState == ViewState.ADD) {
                    noteRepo.insertNote(note)
                } else {
                    noteRepo.updateNote(note)
                }
                state = state.copy(viewState = ViewState.VIEW)
            }
        }
    }


    sealed interface PatientNotesEvents {
        data object ChangeToViewNotes : PatientNotesEvents
        data object OnAddNoteClicked : PatientNotesEvents
        data class OnNoteClicked(val note: PatientNote) : PatientNotesEvents
        data class OnNoteDeleteClicked(val note: PatientNote) : PatientNotesEvents
        data class GetNotes(val patient: Patient) : PatientNotesEvents
        data class UpsertNote(val patient: Patient?) : PatientNotesEvents
        class NoteTextChange(val note: String) : PatientNotesViewModel.PatientNotesEvents
    }
}