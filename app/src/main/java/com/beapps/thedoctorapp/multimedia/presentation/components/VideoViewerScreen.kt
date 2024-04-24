package com.beapps.thedoctorapp.multimedia.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.beapps.thedoctorapp.multimedia.presentation.MediaState
import com.beapps.thedoctorapp.multimedia.presentation.MediaViewModel

@Composable
fun VideoViewerScreen(videoState: MediaState.VideoState , onEvent : (MediaViewModel.MediaViewModelEvents) -> Unit ,  modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize() , contentAlignment = Alignment.Center) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = videoState.player
                }
            }
        )
    }

    LaunchedEffect(true) {
        onEvent(MediaViewModel.MediaViewModelEvents.PlayVideo(videoState.url))
    }
}