package com.beapps.thedoctorapp.multimedia.presentation

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.multimedia.domain.MediaType
import com.beapps.thedoctorapp.multimedia.domain.MimeTypes
import com.beapps.thedoctorapp.multimedia.domain.extractMimeType

@Composable
fun MediaScreen(patientContent: PatientContent?, navController: NavHostController) {
    val viewModel = hiltViewModel<MediaViewModel>()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

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

        val imageBitmap = remember(viewModel.mediaState) {
            if (viewModel.mediaState is MediaType.Image) {
                (viewModel.mediaState as MediaType.Image).let {
                    BitmapFactory.decodeByteArray(it.byteArray, 0, it.byteArray.size)
                        .asImageBitmap()
                }
            } else null
        }


        viewModel.mediaState?.let {
            when (it) {
                is MediaType.Image -> {
                    imageBitmap?.let {
                        Image(bitmap = imageBitmap, contentDescription = "Image")
                    }
                }

                is MediaType.TextFile -> {
                    Text(text = it.content)
                }
            }
        }
    }


}
