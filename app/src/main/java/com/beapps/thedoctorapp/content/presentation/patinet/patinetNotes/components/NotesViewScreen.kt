package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesState
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesViewScreen(
    modifier: Modifier = Modifier,
    state: PatientNotesState,
    onEvent: (PatientNotesViewModel.PatientNotesEvents) -> Unit,
    patient: Patient?
) {


    patient?.let {

        LaunchedEffect(key1 = true) {
            onEvent(PatientNotesViewModel.PatientNotesEvents.GetNotes(patient))
        }


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(state.notes, key = { it.id }) { note ->
                    PatientNoteItem(
                        modifier = Modifier.animateItemPlacement(),
                        note = note,
                        onItemClicked = {
                            onEvent(PatientNotesViewModel.PatientNotesEvents.OnNoteClicked(it))
                        },
                        onDeleteClicked = {
                            onEvent(PatientNotesViewModel.PatientNotesEvents.OnNoteDeleteClicked(it))
                        }
                    )
                    HorizontalDivider()
                }
            }
        }

    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "An Error Occurred Please Refresh and Try Again"
        )
    }
}