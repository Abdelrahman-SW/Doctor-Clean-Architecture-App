package com.beapps.thedoctorapp.content.presentation.patientContent


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.domain.MimeTypes
import com.beapps.thedoctorapp.content.domain.extractFolderName
import com.beapps.thedoctorapp.content.presentation.patientContent.components.PatientContentItem
import com.beapps.thedoctorapp.content.presentation.patients.components.PatientItem
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun PatientContentScreen(
    navController: NavController,
    patientFolderName: String,
    doctorId: String,
    screenState: PatientContentScreenState,
    onEvent: (PatientsContentViewModel.PatientContentScreenEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        LaunchedEffect(key1 = true) {
            onEvent(PatientsContentViewModel.PatientContentScreenEvents.GetPatientContent(doctorId , patientFolderName))
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(screenState.patientsContent) { patientContent ->
                PatientContentItem(patientContent = patientContent, onClick = {
                    onEvent(PatientsContentViewModel.PatientContentScreenEvents.PatientContentClicked(it))
                })
                Spacer(modifier = Modifier.height(16.dp))
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
fun PatientContentScreenRoot(navController: NavController, patientFolderName: String, doctorId: String) {
    val viewModel = hiltViewModel<PatientsContentViewModel>()
    PatientContentScreen(navController, patientFolderName, doctorId , viewModel.screenState , viewModel::onEvent)
}