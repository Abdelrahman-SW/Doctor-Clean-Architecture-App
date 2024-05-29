package com.beapps.thedoctorapp.content.presentation.patinet.patientActions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientActionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    patient: Patient?
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = "${patient?.name ?: ""} ${patient?.surname ?: ""}",
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
                }
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
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
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "patient",
                    patient
                )
                navController.navigate(
                    Screen.PatientNotesScreen.route
                )
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
}