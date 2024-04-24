package com.beapps.thedoctorapp.multimedia.domain

enum class MimeTypes (val type : String) {
    Image ("image") ,
    Video ("video") ,
    Text ("text") ,
    Pdf("pdf") ,
    OctetStream("octet-stream") ,
    Undefined(""),
    Others("")
}