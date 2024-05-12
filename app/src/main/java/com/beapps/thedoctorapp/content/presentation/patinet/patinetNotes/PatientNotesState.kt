package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import com.beapps.thedoctorapp.content.domain.models.PatientNote

data class PatientNotesState(
    val notes: List<PatientNote> = emptyList(),
    val viewState: ViewState = ViewState.VIEW,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedNote: PatientNote? = null,
    val noteText: String = ""
)

enum class ViewState {
    ADD,
    EDIT,
    VIEW
}