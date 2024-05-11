package com.beapps.thedoctorapp.content.presentation.patinet.patientActions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.core.presentation.Screen

@Composable
fun PatientActionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    patient: Patient?
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "patient",
                patient
            )
            navController.navigate(
                Screen.PatientsFilesScreen.route
            )
        }) {
            Text(text = "View Files")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {

        }) {
            Text(text = "View Notes")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "patient",
                patient
            )
            navController.navigate(
                Screen.PatientInfoScreen.route
            )
        }) {
            Text(text = "Contact Info")
        }
    }
}