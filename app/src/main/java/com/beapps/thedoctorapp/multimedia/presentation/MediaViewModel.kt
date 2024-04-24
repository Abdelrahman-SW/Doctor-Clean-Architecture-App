package com.beapps.thedoctorapp.multimedia.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import com.beapps.thedoctorapp.multimedia.domain.MediaType
import com.beapps.thedoctorapp.multimedia.domain.extractMimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaDownloaderManager: MediaDownloaderManager
) : ViewModel() {
    var mediaState by mutableStateOf<MediaType?>(null)
        private set

    private fun downloadMediaFile(filePath: String, mimeType : String?) {
        viewModelScope.launch {
            val media = mediaDownloaderManager.downloadMediaFile(filePath , mimeType.extractMimeType())
            when(media) {
                is Result.Error -> {

                }
                is Result.Success -> {
                    mediaState = when (val data = media.data) {
                        is MediaType.Image -> {
                            MediaType.Image(data.byteArray)
                        }

                        is MediaType.TextFile -> {
                            MediaType.TextFile(data.content)
                        }
                    }
                }
            }
        }

    }

    fun onEvent (event: MediaViewModelEvents) {
        when(event) {
            is MediaViewModelEvents.DownloadMedia -> downloadMediaFile(event.filePath , event.mimeType)
        }
    }

    sealed interface MediaViewModelEvents {
        data class DownloadMedia (val filePath: String , val mimeType : String?) : MediaViewModelEvents
    }


}