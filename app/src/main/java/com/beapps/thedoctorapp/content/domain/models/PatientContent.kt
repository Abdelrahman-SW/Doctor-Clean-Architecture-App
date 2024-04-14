package com.beapps.thedoctorapp.content.domain.models

import com.beapps.thedoctorapp.content.domain.MimeTypes

data class PatientContent(
    val path: String ,
    val name: String = "",
    val sizeBytes: Long,
    val mimeType: MimeTypes,
    val updatedTimeMillis: Long
)
