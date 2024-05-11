package com.beapps.thedoctorapp.content.presentation.patinet.patientsList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.presentation.patinet.patientsList.components.PatientItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun PatientsListScreen(navController: NavController, screenState: PatientsListScreenState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(screenState.patients) { patient ->
                PatientItem(patient = patient, onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "patient",
                         patient
                    )
                    navController.navigate(
                        Screen.PatientActionsScreen.route
                    )
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
fun PatientScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<PatientsListViewModel>()
    PatientsListScreen(navController, viewModel.screenState)
}