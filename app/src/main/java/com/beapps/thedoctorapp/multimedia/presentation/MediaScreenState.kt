package com.beapps.thedoctorapp.multimedia.presentation

import androidx.media3.common.Player

data class MediaScreenState (
    val isLoading : Boolean =  false ,
    val error : String? = null ,
    val mediaState : MediaState = MediaState.None
)

sealed interface MediaState {
    data object None : MediaState
    data class ImageState(val imageUrl: String , val isImageLoading : Boolean) : MediaState
    data class TextState (val text: String) : MediaState
    data class VideoState (val url: String , val player: Player) : MediaState
}