package com.beapps.thedoctorapp.content.data.firebase

import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.google.firebase.storage.StorageMetadata

fun StorageMetadata.toPatientContent(): PatientContent {
    return PatientContent(
        path = path,
        sizeBytes = sizeBytes,
        updatedTimeMillis = updatedTimeMillis,
        mimeType = contentType
    )
}