package com.beapps.thedoctorapp.multimedia.presentation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import com.beapps.thedoctorapp.multimedia.domain.MediaDownloaderManager
import com.beapps.thedoctorapp.multimedia.domain.models.MediaContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaDownloaderManager: MediaDownloaderManager,
    private val player: Player
) : ViewModel() {
    var mediaScreenState by mutableStateOf(MediaScreenState())
        private set

    private fun downloadMediaFile(filePath: String, mimeType: String?) {
        viewModelScope.launch {
            mediaScreenState = mediaScreenState.copy(isLoading = true , error = null)
            when (val result = mediaDownloaderManager.downloadMediaFile(filePath, mimeType)) {
                is Result.Error -> {
                    mediaScreenState = when (result.error) {
                        Error.DownloadMediaErrors.EmptyContent -> {
                            mediaScreenState.copy(isLoading = false , error = "No Content Found")
                        }

                        is Error.DownloadMediaErrors.Others -> {
                            mediaScreenState.copy(isLoading = false , error = result.error.message ?: "UnKnown Error")
                        }

                        Error.DownloadMediaErrors.UnSupportedMedia -> {
                            mediaScreenState.copy(isLoading = false , error = "This Media File Is Not Supported !")
                        }
                    }

                }

                is Result.Success -> {
                    mediaScreenState = when (val data = result.data) {
                        is MediaContent.Image -> {
                            mediaScreenState.copy(isLoading = false , mediaState = MediaState.ImageState(data.imageUrl , true))
                        }

                        is MediaContent.TextFile -> {
                            mediaScreenState.copy(isLoading = false , mediaState = MediaState.TextState(data.text))
                        }

                        is MediaContent.Video -> {
                            mediaScreenState.copy(isLoading = false , mediaState = MediaState.VideoState(data.url , player =  player))
                        }
                    }
                }
            }
        }

    }

    fun onEvent(event: MediaViewModelEvents) {
        when (event) {
            is MediaViewModelEvents.DownloadMedia -> downloadMediaFile(
                event.filePath,
                event.mimeType
            )

            is MediaViewModelEvents.PlayVideo -> playVideo(event.videoUrl)
            MediaViewModelEvents.ImageLoaded -> { mediaScreenState = mediaScreenState.copy(mediaState = (mediaScreenState.mediaState as MediaState.ImageState).copy(isImageLoading = false))}
        }
    }

    private fun playVideo(videoUrl: String) {
        player.apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            play()
        }
    }

    sealed interface MediaViewModelEvents {
        data object ImageLoaded : MediaViewModel.MediaViewModelEvents
        data class DownloadMedia(val filePath: String, val mimeType: String?) : MediaViewModelEvents
        data class PlayVideo(val videoUrl : String) : MediaViewModelEvents
    }

    override fun onCleared() {
        player.release()
        super.onCleared()
    }
}