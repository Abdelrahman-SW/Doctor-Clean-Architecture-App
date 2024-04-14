package com.beapps.thedoctorapp.content.domain

enum class MimeTypes (val type : String) {
    Image ("image") ,
    Video ("video") ,
    Text ("text") ,
    Pdf("pdf") ,
    OctetStream("octet-stream") ,
    Undefined(""),
    Others("")
}