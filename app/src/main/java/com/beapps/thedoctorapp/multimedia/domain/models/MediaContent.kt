package com.beapps.thedoctorapp.multimedia.domain.models

sealed class MediaContent () {
    data class TextFile (val text : String) : MediaContent()
    data class Image (val byteArray: ByteArray) : MediaContent()
}