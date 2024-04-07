package com.beapps.thedoctorapp.home.presentation

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
import androidx.navigation.NavController
import com.beapps.thedoctorapp.auth.domain.Doctor
import com.beapps.thedoctorapp.core.domain.Util
import com.beapps.thedoctorapp.core.presentation.Screen


@Composable
fun HomeScreen(navController: NavController , doctor: Doctor) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Name = ${doctor.name}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Id = ${doctor.id}")
        Spacer(modifier = Modifier.height(32.dp))
        val context = LocalContext.current
        Button(onClick = {
            Util.deleteDoctorCredentials(context)
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }) {
            Text(text = "Logout")
        }

    }

}