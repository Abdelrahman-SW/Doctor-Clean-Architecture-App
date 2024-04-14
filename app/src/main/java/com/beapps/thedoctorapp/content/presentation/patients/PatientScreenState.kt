package com.beapps.thedoctorapp.content.presentation.patients

import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.core.domain.Error

data class PatientScreenState(
    val doctorId : String = "",
    val isLoading: Boolean = false,
    val error : Error.GetContentErrors? = null,
    val patients : List<Patient> = emptyList()
)