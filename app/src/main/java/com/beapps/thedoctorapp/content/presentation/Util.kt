package com.beapps.thedoctorapp.content.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.ui.graphics.vector.ImageVector

fun String?.getVectorIconFromMimeType(): ImageVector {
    return when {
        isNullOrEmpty() -> Icons.Default.Folder
        contains("image") -> Icons.Default.Image
        contains("video") -> Icons.Default.VideoFile
        contains("text") -> Icons.Default.TextFormat
        else -> Icons.Default.Folder
    }
}