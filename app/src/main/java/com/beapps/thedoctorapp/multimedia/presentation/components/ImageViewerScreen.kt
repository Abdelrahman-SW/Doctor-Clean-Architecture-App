package com.beapps.thedoctorapp.multimedia.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import coil.compose.AsyncImage
import com.beapps.thedoctorapp.multimedia.presentation.MediaState
import com.beapps.thedoctorapp.multimedia.presentation.MediaViewModel

@Composable
fun ImageViewerScreen(
    imageState: MediaState.ImageState,
    onEvent: (MediaViewModel.MediaViewModelEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (imageState.isImageLoading) {
            CircularProgressIndicator()
        }
        AsyncImage(
            model = imageState.imageUrl,
            contentDescription = "Image From Firebase",
            onSuccess = {
                onEvent(MediaViewModel.MediaViewModelEvents.ImageLoaded)
            }
        )
    }
}