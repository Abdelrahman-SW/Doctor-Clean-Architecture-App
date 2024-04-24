package com.beapps.thedoctorapp.multimedia.domain

sealed class MediaType {
    data class TextFile (val content : String) : MediaType()
    data class Image (val byteArray: ByteArray) : MediaType()
}