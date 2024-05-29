package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesState
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesViewModel
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.ViewState

@Composable
fun UpsertNoteScreen(
    modifier: Modifier = Modifier,
    state: PatientNotesState,
    onEvent: (PatientNotesViewModel.PatientNotesEvents) -> Unit,
    patient: Patient?
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.viewState == ViewState.ADD) {
            TextField(
                label = { Text(text = "Title (can`t be changed later)") },
                value =  state.noteTitle,
                onValueChange = {
                    onEvent(PatientNotesViewModel.PatientNotesEvents.NoteTitleChange(it))
                })
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextField(
            label = { Text(text = "Description") },
            value = if (state.viewState == ViewState.ADD) state.noteText else state.selectedNote?.note
                ?: "",
            onValueChange = {
                onEvent(PatientNotesViewModel.PatientNotesEvents.NoteTextChange(it))
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onEvent(PatientNotesViewModel.PatientNotesEvents.UpsertNote(patient))
        }) {
            Text(text = if (state.viewState == ViewState.ADD) "Add Note" else "Update Note")
        }

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }

}
