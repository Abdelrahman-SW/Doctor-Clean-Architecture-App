package com.beapps.thedoctorapp.content.presentation.patinet.patientsList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beapps.thedoctorapp.core.domain.Patient

@Composable
fun PatientItem(
    patient: Patient,
    modifier: Modifier = Modifier,
    onClick: (patient: Patient) -> Unit
) {
    Column(modifier = Modifier
        .clickable {
            onClick(patient)
        }
        .padding(16.dp) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
        {
            Icon(
                Icons.Default.Face,
                contentDescription = "patient Icon",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = "${patient.name} ${patient.surname}",
                textAlign = TextAlign.Center
            )
        }

}