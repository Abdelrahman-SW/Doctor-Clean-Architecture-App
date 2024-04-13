package com.beapps.thedoctorapp.content.presentation.patients

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun PatientScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel = hiltViewModel<PatientsViewModel>()

    }
}

@Composable
fun HomeScreenRoot(navController: NavController, doctor: Doctor) {
//    val viewModel = hiltViewModel<HomeViewModel>()
//    HomeScreen(navController, doctor, viewModel::onEvent)
}