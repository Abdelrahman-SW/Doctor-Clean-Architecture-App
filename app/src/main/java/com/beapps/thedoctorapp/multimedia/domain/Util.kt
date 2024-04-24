package com.beapps.thedoctorapp.multimedia.domain


fun String?.extractMimeType(): MimeTypes {
    return when {
        isNullOrEmpty() -> MimeTypes.Undefined
        contains(MimeTypes.Image.type) -> MimeTypes.Image
        contains(MimeTypes.Video.type) -> MimeTypes.Video
        contains(MimeTypes.Text.type) -> MimeTypes.Text
        contains(MimeTypes.Pdf.type) -> MimeTypes.Pdf
        contains(MimeTypes.OctetStream.type) -> MimeTypes.OctetStream
        else -> MimeTypes.Others
    }
}
