package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles

import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Error

data class PatientFilesScreenState(
    val isLoading: Boolean = false,
    val error : Error.GetContentErrors? = null,
    val patientFiles : List<PatientFile> = emptyList(),
)
