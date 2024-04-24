package com.beapps.thedoctorapp.multimedia.presentation

data class MediaScreenState (
    val isLoading : Boolean =  false ,
    val error : String? = null ,
    val mediaState : MediaState = MediaState.None
)

sealed interface MediaState {
    data object None : MediaState
    data class ImageState (val byteArray: ByteArray) : MediaState
    data class TextState (val text: String) : MediaState
}