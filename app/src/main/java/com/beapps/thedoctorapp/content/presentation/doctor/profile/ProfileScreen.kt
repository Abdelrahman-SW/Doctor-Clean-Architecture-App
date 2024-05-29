package com.beapps.thedoctorapp.content.presentation.doctor.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beapps.thedoctorapp.core.domain.Doctor


@Composable
fun ProfileScreen(
    doctor: Doctor,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Name = ${doctor.name}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Id = ${doctor.id}")
        Spacer(modifier = Modifier.height(32.dp))
    }

}

@Composable
fun ProfileScreenRoot(doctor: Doctor) {
    //val viewModel = hiltViewModel<HomeViewModel>()
    ProfileScreen(doctor)
}