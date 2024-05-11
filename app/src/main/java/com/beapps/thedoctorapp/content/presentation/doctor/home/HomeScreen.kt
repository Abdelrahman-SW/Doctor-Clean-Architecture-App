package com.beapps.thedoctorapp.content.presentation.doctor.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.presentation.Screen
import com.beapps.thedoctorapp.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onEvent: (HomeScreenEvents) -> Unit,
    doctor: Doctor?
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        text = "Welcome Back, ${doctor?.name}",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController.navigate(Screen.PatientsListScreen.route)
            }) {
                Text(text = "My Patients")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                navController.navigate(Screen.ProfileScreen.route)
            }) {
                Text(text = "My Profile")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                onEvent(HomeScreenEvents.Logout)
                navController.popBackStack()
                navController.navigate(Screen.LoginScreen.route)
            }) {
                Text(text = "Logout")
            }

        }
    }

}

@Composable
fun HomeScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(navController, viewModel::onEvent , viewModel.doctor)
}