package com.beapps.thedoctorapp.content.presentation.patinet.patientFiles

import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Error

data class PatientFilesScreenState(
    val showGraphsOnly : Boolean = false,
    val searchQuery : String = "",
    val isLoading: Boolean = false,
    val error : Error.GetContentErrors? = null,
    val allPatientFiles : List<PatientFile> = emptyList(),
    val filteredPatientFiles : List<PatientFile> = emptyList(),
)
