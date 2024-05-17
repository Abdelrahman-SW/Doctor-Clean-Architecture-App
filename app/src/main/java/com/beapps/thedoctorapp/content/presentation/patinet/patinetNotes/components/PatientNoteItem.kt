package com.beapps.thedoctorapp.content.presentation.patinet.patinetNotes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.content.presentation.toReadableDate
import com.beapps.thedoctorapp.ui.theme.Purple40

@Composable
fun PatientNoteItem(
    modifier: Modifier = Modifier,
    note: PatientNote,
    onItemClicked: (PatientNote) -> Unit,
    onDeleteClicked: (PatientNote) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClicked(note) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = note.id
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = note.createdAt.toReadableDate(),
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        IconButton(onClick = {onDeleteClicked(note)}) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete" , tint = Purple40)
        }
    }
}