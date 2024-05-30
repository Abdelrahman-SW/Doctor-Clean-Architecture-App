package com.beapps.thedoctorapp.multimedia.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.multimedia.presentation.components.ImageViewerScreen
import com.beapps.thedoctorapp.multimedia.presentation.components.TextViewerScreen
import com.beapps.thedoctorapp.multimedia.presentation.components.VideoViewerScreen
import com.beapps.thedoctorapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(patientFile: PatientFile?, navController: NavHostController) {
    val viewModel = hiltViewModel<MediaViewModel>()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                title = {
                    Text(
                        fontSize = 14.sp,
                        text = patientFile?.name ?: "Unknown Name",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }

    ) { padding ->

        LaunchedEffect(key1 = true) {
            patientFile?.let {
                viewModel.onEvent(
                    MediaViewModel.MediaViewModelEvents.DownloadMedia(
                        patientFile.path,
                        patientFile.mimeType
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



        when (val state = mediaScreenState.mediaState) {
            MediaState.None -> Unit
            is MediaState.ImageState -> {
                ImageViewerScreen(modifier =  Modifier.padding(padding), imageState = state, onEvent = viewModel::onEvent)
            }

            is MediaState.TextState -> {
                TextViewerScreen(modifier =  Modifier.padding(padding) , textState = state)
            }

            is MediaState.VideoState -> {
                VideoViewerScreen(modifier =  Modifier.padding(padding) , videoState = state, onEvent = viewModel::onEvent)
            }
        }
    }

}