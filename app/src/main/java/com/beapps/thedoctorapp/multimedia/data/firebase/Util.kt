package com.beapps.thedoctorapp.multimedia.data.firebase


fun String?.extractMimeType(): FirebaseMimeType {
    return when {
        isNullOrEmpty() -> FirebaseMimeType.Undefined
        contains(FirebaseMimeType.Image.type) -> FirebaseMimeType.Image
        contains(FirebaseMimeType.Video.type) -> FirebaseMimeType.Video
        contains(FirebaseMimeType.Text.type) -> FirebaseMimeType.Text
//        contains(FirebaseMimeType.Pdf.type) -> FirebaseMimeType.Pdf
//        contains(FirebaseMimeType.OctetStream.type) -> FirebaseMimeType.OctetStream
        else -> FirebaseMimeType.Others
    }
}
