package com.beapps.thedoctorapp.content.presentation.patinet.patientsList

import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.core.domain.Error

data class PatientsListScreenState(
    val searchQuery : String = "",
    val isLoading: Boolean = false,
    val error : Error.GetContentErrors? = null,
    val allPatients : List<Patient> = emptyList(),
    val filteredPatients : List<Patient> = emptyList()
)