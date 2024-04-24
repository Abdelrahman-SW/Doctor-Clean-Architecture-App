package com.beapps.thedoctorapp.multimedia.presentation

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.multimedia.presentation.components.ImageViewerScreen
import com.beapps.thedoctorapp.multimedia.presentation.components.TextViewerScreen

@Composable
fun MediaScreen(patientContent: PatientContent?, navController: NavHostController) {
    val viewModel = hiltViewModel<MediaViewModel>()

    LaunchedEffect(key1 = true) {
        patientContent?.let {
            viewModel.onEvent(
                MediaViewModel.MediaViewModelEvents.DownloadMedia(
                    patientContent.path,
                    patientContent.mimeType
                )
            )
        }
    }

    val mediaScreenState = viewModel.mediaScreenState

    if (mediaScreenState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (mediaScreenState.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = mediaScreenState.error)
        }
    }


    val imageBitmap = remember(mediaScreenState.mediaState) {
        if (mediaScreenState.mediaState is MediaState.ImageState) {
            mediaScreenState.mediaState.let {
                BitmapFactory.decodeByteArray(it.byteArray, 0, it.byteArray.size)
                    .asImageBitmap()
            }
        } else null
    }


    when(val state = mediaScreenState.mediaState) {
        MediaState.None -> Unit
        is MediaState.ImageState -> {
            ImageViewerScreen(imageBitmap = imageBitmap)
        }
        is MediaState.TextState -> {
            TextViewerScreen(state =  state)
        }
    }
}


