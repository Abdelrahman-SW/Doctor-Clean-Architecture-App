package com.beapps.thedoctorapp.content.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val id : String = "",
    val assignedDoctorId : String = "",
    val name: String,
    val surname: String,
    val email : String ,
    val phone : String
): Parcelable