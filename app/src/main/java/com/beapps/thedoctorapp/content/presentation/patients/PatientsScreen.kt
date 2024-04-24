package com.beapps.thedoctorapp.content.presentation.patients

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.beapps.thedoctorapp.content.domain.extractFolderName
import com.beapps.thedoctorapp.content.presentation.patients.components.PatientItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun PatientScreen(navController: NavController, screenState: PatientScreenState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize() , horizontalAlignment = Alignment.CenterHorizontally) {
            items(screenState.patients) { patient ->
                PatientItem(patient = patient, onClick = {
                    navController.navigate(
                        Screen.PatientsContentsScreen.withArgs(
                            it.extractFolderName(),
                            screenState.doctorId
                        )
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
    val viewModel = hiltViewModel<PatientsViewModel>()
    PatientScreen(navController, viewModel.screenState)
}