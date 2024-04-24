package com.beapps.thedoctorapp.content.presentation.patientContent.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.content.presentation.getVectorIconFromMimeType
import com.beapps.thedoctorapp.multimedia.data.firebase.FirebaseMimeType
import com.beapps.thedoctorapp.multimedia.data.firebase.extractMimeType

@Composable
fun PatientContentItem(
    patientContent: PatientContent,
    modifier: Modifier = Modifier,
    onClick: (patientContent: PatientContent) -> Unit
) {
    Column(modifier = Modifier
        .clickable {
            onClick(patientContent)
        }
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = patientContent.mimeType.getVectorIconFromMimeType(),
            contentDescription = "Media Icon",
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = patientContent.name, textAlign = TextAlign.Center
        )
    }
}