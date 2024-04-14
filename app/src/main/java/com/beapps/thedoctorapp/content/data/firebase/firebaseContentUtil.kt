package com.beapps.thedoctorapp.content.data.firebase

import com.beapps.thedoctorapp.content.domain.MimeTypes
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.google.firebase.storage.StorageMetadata

fun StorageMetadata.toPatientContent(): PatientContent {
    return PatientContent(
        path = path,
        sizeBytes = sizeBytes,
        updatedTimeMillis = updatedTimeMillis,
        mimeType = contentType.extractMimeType()
    )
}

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
