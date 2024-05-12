package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components.AddNoteScreen
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components.NotesViewScreen
import com.beapps.thedoctorapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientNotesScreen(modifier: Modifier = Modifier , navController: NavController , patient : Patient?) {
    val viewModel = hiltViewModel<PatientNotesViewModel>()
    val state = viewModel.state
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = "Notes",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onAddNoteClicked()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                            contentDescription = "Add Note",
                            tint = Color.White
                        )
                    }
                }
            )
        }

    ) { padding ->
        when (state.viewState) {
            ViewState.ADD -> AddNoteScreen(modifier = Modifier.padding(padding) , state)
            ViewState.VIEW -> NotesViewScreen(modifier = Modifier.padding(padding) , state)
        }
    }
}