package com.beapps.thedoctorapp.content.presentation.patinet.patientInfo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.content.domain.models.Patient

@Composable
fun PatientInfoScreen(modifier: Modifier = Modifier , patient: Patient?) {
     patient?.let {
         Text(text = "Patient : $it")
     }
}