package com.beapps.thedoctorapp.content.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextFormat
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.ui.graphics.vector.ImageVector
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Locale

fun String?.getVectorIconFromMimeType(): ImageVector {
    return when {
        isNullOrEmpty() -> Icons.Default.Folder
        contains("image") -> Icons.Default.Image
        contains("video") -> Icons.Default.VideoFile
        contains("text") -> Icons.Default.TextFormat
        else -> Icons.Default.Folder
    }
}

fun Long.toReadableDate () : String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd    hh:mm a", Locale.getDefault())
    val formattedDateTime = dateFormat.format(this)
    return formattedDateTime
}