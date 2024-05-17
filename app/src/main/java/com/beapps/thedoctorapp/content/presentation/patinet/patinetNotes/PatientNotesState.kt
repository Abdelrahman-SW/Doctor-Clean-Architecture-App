package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error

data class PatientNotesState(
    val notes: List<PatientNote> = emptyList(),
    val viewState: ViewState = ViewState.VIEW,
    val isLoading: Boolean = false,
    val error: Error.ManageNotesErrors? = null,
    val selectedNote: PatientNote? = null,
    val noteText: String = "",
    val noteTitle: String = ""
)

enum class ViewState {
    ADD,
    EDIT,
    VIEW
}