@file:JvmName("PatientFilesViewModelKt")

package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.presentation.patinet.patientFiles.components.PatientFileItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun PatientFilesScreen(
    navController: NavController,
    patient: Patient?,
    screenState: PatientFilesScreenState,
    onEvent: (PatientFilesViewModel.PatientContentScreenEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = true) {
            patient?.let {
                onEvent(
                    PatientFilesViewModel.PatientContentScreenEvents.GetPatientFiles(
                        patient
                    )
                )
            }

        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(screenState.patientFiles) { patientContent ->
                PatientFileItem(patientFile = patientContent, onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "patientContent",
                        patientContent
                    )
                    navController.navigate(Screen.MultiMediaScreen.route)
                })
                HorizontalDivider()
            }
        }

        screenState.error?.let { error ->
            val errorMessage = when (error) {
                Error.GetContentErrors.EmptyContent -> "No data To Display"
                is Error.GetContentErrors.Others -> error.message ?: "UnKnown Error"
            }
            Text(text = errorMessage)
        }

        if (screenState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PatientContentScreenRoot(
    navController: NavController,
    patient: Patient?
) {
    val viewModel = hiltViewModel<PatientFilesViewModel>()
    PatientFilesScreen(
        navController,
        patient,
        viewModel.screenState,
        viewModel::onEvent
    )
}