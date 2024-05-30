package com.beapps.thedoctorapp.multimedia.presentation.components

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.beapps.thedoctorapp.multimedia.presentation.MediaState
import com.beapps.thedoctorapp.multimedia.presentation.MediaViewModel

@Composable
fun ImageViewerScreen(
    imageState: MediaState.ImageState,
    onEvent: (MediaViewModel.MediaViewModelEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember {
        mutableStateOf(Offset.Zero)
    }
    var size by remember { mutableStateOf(IntSize.Zero) }


    Box(modifier = modifier
        .fillMaxSize()
        .onSizeChanged { size = it }
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            translationX = offset.x
            translationY = offset.y
        }
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->

                scale = (scale * zoom).coerceIn(1f, 5f)

                val extraWidth = (scale - 1) * size.width
                val extraHeight = (scale - 1) * size.height

                val maxX = extraWidth / 2
                val maxY = extraHeight / 2

                offset = Offset(
                    x = (offset.x + scale * pan.x).coerceIn(-maxX, maxX),
                    y = (offset.y + scale * pan.y).coerceIn(-maxY, maxY),
                )
            }
        },
        contentAlignment = Alignment.Center
    ) {
        if (imageState.isImageLoading) {
            CircularProgressIndicator()
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageState.imageUrl)
                .size(Size.ORIGINAL) // Request the original size to prevent blurring
                .build(),
            contentDescription = "Image From Firebase",
            onSuccess = {
                onEvent(MediaViewModel.MediaViewModelEvents.ImageLoaded)
            }
        )
    }
}