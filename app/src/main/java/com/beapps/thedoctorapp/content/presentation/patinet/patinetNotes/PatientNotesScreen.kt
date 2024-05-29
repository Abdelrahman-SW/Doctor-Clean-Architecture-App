package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components.UpsertNoteScreen
import com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components.NotesViewScreen
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientNotesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    patient: Patient?
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<PatientNotesViewModel>()
    val state = viewModel.state
    val onBackPressed: () -> Unit = remember(state.viewState)
    {
        {
            when (state.viewState) {
                ViewState.ADD -> viewModel.onEvent(PatientNotesViewModel.PatientNotesEvents.ChangeToViewNotes)
                ViewState.EDIT -> viewModel.onEvent(PatientNotesViewModel.PatientNotesEvents.ChangeToViewNotes)
                ViewState.VIEW -> navController.popBackStack()
            }
        }
    }

    BackHandler {
        onBackPressed()
    }


    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            val message = when (it) {
                is Error.ManageNotesErrors.Others -> it.message ?: "An Error Occurred"
                Error.ManageNotesErrors.TitleAlreadyExists -> "Title Already Exits"
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onErrorConsumed()
        }
    }


    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = when (state.viewState) {
                            ViewState.ADD -> "Add Note"
                            ViewState.EDIT -> "Update Note"
                            ViewState.VIEW -> "Notes"
                        },
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    if (state.viewState == ViewState.VIEW) {
                        IconButton(onClick = {
                            viewModel.onEvent(PatientNotesViewModel.PatientNotesEvents.OnAddNoteClicked)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.NoteAdd,
                                contentDescription = "Add Note",
                                tint = Color.White
                            )
                        }
                    }
                }
            )
        }

    ) { padding ->
        when (state.viewState) {
            ViewState.ADD -> UpsertNoteScreen(
                modifier = Modifier.padding(padding),
                state,
                viewModel::onEvent,
                patient
            )

            ViewState.VIEW -> NotesViewScreen(
                modifier = Modifier.padding(padding),
                state,
                viewModel::onEvent,
                patient
            )

            ViewState.EDIT -> UpsertNoteScreen(
                modifier = Modifier.padding(padding),
                state,
                viewModel::onEvent,
                patient
            )
        }
    }
}