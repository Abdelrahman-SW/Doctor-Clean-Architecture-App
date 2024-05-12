package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.PatientNotesState

@Composable
fun NotesViewScreen(modifier: Modifier = Modifier, state: PatientNotesState) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "View Note")
    }

}