package com.beapps.thedoctorapp.content.presentation.patients.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.content.domain.models.Patient

@Composable
fun PatientItem(patient: Patient, modifier: Modifier = Modifier, onClick : (patient: Patient)-> Unit) {
    Text(
        text = "name = ${patient.name} ${patient.surname}",
        modifier = modifier.clickable {
           onClick(patient)
        }
    )
}