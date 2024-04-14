package com.beapps.thedoctorapp.content.presentation.home

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
fun HomeScreen(
    navController: NavController,
    onEvent: (HomeScreenEvents) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate(Screen.PatientsScreen.route)
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

@Composable
fun HomeScreenRoot(navController: NavController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    HomeScreen(navController, viewModel::onEvent)
}