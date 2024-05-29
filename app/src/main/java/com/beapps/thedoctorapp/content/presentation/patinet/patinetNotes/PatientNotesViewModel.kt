package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.content.domain.PatientNotesManager
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientNotesViewModel @Inject constructor(
    private val patientNotesManager: PatientNotesManager
) : ViewModel() {

    private var getNotesFired = false
    var state by mutableStateOf(PatientNotesState())
        private set

    private fun onAddNoteClicked() {
        state = state.copy(viewState = ViewState.ADD, noteText = "", noteTitle = "")
    }

    private fun onNoteClicked(note: PatientNote) {
        state = state.copy(
            viewState = ViewState.EDIT,
            selectedNote = note,
            isLoading = true
        )
        viewModelScope.launch {
            val result = patientNotesManager.getNoteContent(note)
            state = when (result) {
                is Result.Error -> state.copy(error = result.error, isLoading = false)
                is Result.Success -> {
                    state.copy(
                        isLoading = false,
                        selectedNote = state.selectedNote?.copy(note = result.data)
                    )
                }
            }
        }
    }

    private fun changeToViewNotes() {
        state = state.copy(viewState = ViewState.VIEW)
    }

    private fun getNotes(patient: Patient) {
        if (getNotesFired) return
        getNotesFired = true
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = patientNotesManager.getPatientNotes(patient.id, patient.assignedDoctorId)
            state = when (result) {
                is Result.Error -> state.copy(error = result.error, isLoading = false)
                is Result.Success -> state.copy(notes = result.data, isLoading = false)
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

    private fun onNoteTitleChange(note: String) {
        state = state.copy(noteTitle = note)
    }


    fun onEvent(event: PatientNotesEvents) {
        when (event) {
            is PatientNotesEvents.ChangeToViewNotes -> changeToViewNotes()
            is PatientNotesEvents.GetNotes -> getNotes(event.patient)
            is PatientNotesEvents.OnAddNoteClicked -> onAddNoteClicked()
            is PatientNotesEvents.OnNoteClicked -> onNoteClicked(event.note)
            is PatientNotesEvents.UpsertNote -> upsertNote(event.patient)
            is PatientNotesEvents.NoteTextChange -> onNoteTextChange(event.note)
            is PatientNotesEvents.NoteTitleChange -> onNoteTitleChange(event.note)
            is PatientNotesEvents.OnNoteDeleteClicked -> deleteNote(event.note)
            PatientNotesEvents.OnErrorConsumed -> onErrorConsumed()
        }
    }

    fun onErrorConsumed() {
        state = state.copy(error = null)
    }

    private fun deleteNote(note: PatientNote) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = patientNotesManager.deleteNote(note)
            state = when (result) {
                is Result.Error -> state.copy(
                    isLoading = false,
                    error = result.error
                )

                is Result.Success -> state.copy(isLoading = false, notes = result.data)

            }
        }
    }


    private fun upsertNote(patient: Patient?) {
        patient?.let {
            val note = if (state.viewState == ViewState.ADD) {
                PatientNote(
                    id = state.noteTitle,
                    note = state.noteText,
                    byDoctorId = patient.assignedDoctorId,
                    toPatientId = patient.id
                )
            } else {
                state.selectedNote!!
            }
            viewModelScope.launch {
                state = state.copy(isLoading = true)
                val result = if (state.viewState == ViewState.ADD) {
                    patientNotesManager.insertNote(note)
                } else {
                    patientNotesManager.updateNote(note)
                }
                state = when (result) {
                    is Result.Error -> state.copy(
                        isLoading = false,
                        error = result.error
                    )

                    is Result.Success -> state.copy(
                        viewState = ViewState.VIEW,
                        isLoading = false,
                        notes = result.data
                    )

                }
            }
        }
    }


    sealed interface PatientNotesEvents {
        data object ChangeToViewNotes : PatientNotesEvents
        data object OnAddNoteClicked : PatientNotesEvents
        data object OnErrorConsumed : PatientNotesEvents
        data class OnNoteClicked(val note: PatientNote) : PatientNotesEvents
        data class OnNoteDeleteClicked(val note: PatientNote) : PatientNotesEvents
        data class GetNotes(val patient: Patient) : PatientNotesEvents
        data class UpsertNote(val patient: Patient?) : PatientNotesEvents
        class NoteTextChange(val note: String) : PatientNotesEvents
        class NoteTitleChange(val note: String) : PatientNotesEvents
    }
}