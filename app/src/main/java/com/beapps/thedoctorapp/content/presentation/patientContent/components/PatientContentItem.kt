package com.beapps.thedoctorapp.content.presentation.patientContent.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beapps.thedoctorapp.content.domain.models.PatientContent

@Composable
fun PatientContentItem(
    patientContent: PatientContent,
    modifier: Modifier = Modifier,
    onClick: (patientContent: PatientContent) -> Unit
) {
    Text(
        text = "${patientContent.path}%%${patientContent.name} %%${patientContent.mimeType}%%${patientContent.sizeBytes}%%${patientContent.updatedTimeMillis}",
        modifier = modifier.clickable { onClick(patientContent) }
    )
}