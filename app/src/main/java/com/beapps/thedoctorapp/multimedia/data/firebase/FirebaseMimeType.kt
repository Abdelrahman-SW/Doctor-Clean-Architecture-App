package com.beapps.thedoctorapp.multimedia.data.firebase

enum class FirebaseMimeType (val type : String) {
    Image ("image") ,
    Video ("video") ,
    Text ("text") ,
//    Pdf("pdf") ,
//    OctetStream("octet-stream") ,
    Undefined(""),
    Others("")
}