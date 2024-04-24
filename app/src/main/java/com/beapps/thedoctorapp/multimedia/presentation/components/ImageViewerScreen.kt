package com.beapps.thedoctorapp.multimedia.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.beapps.thedoctorapp.multimedia.presentation.MediaScreenState
import com.beapps.thedoctorapp.multimedia.presentation.MediaState

@Composable
fun ImageViewerScreen(imageBitmap: ImageBitmap?, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        imageBitmap?.let {
            Image(bitmap = it, contentDescription = null)
        }
    }
}