package com.beapps.thedoctorapp.content.data.remote.firebase

import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.google.firebase.storage.StorageMetadata

fun StorageMetadata.toPatientFile(): PatientFile {
    return PatientFile(
        path = path,
        sizeBytes = sizeBytes,
        updatedTimeMillis = updatedTimeMillis,
        mimeType = contentType
    )
}