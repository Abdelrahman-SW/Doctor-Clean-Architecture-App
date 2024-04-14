package com.beapps.thedoctorapp.content.presentation.patientContent

import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.core.domain.Error

data class PatientContentScreenState(
    val isLoading: Boolean = false,
    val error : Error.GetContentErrors? = null,
    val patientsContent : List<PatientContent> = emptyList()
)