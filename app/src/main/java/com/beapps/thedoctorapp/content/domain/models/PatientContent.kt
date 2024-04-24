package com.beapps.thedoctorapp.content.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatientContent(
    val path: String ,
    val name: String = "",
    val sizeBytes: Long,
    val mimeType: String?,
    val updatedTimeMillis: Long
) : Parcelable
