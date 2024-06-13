package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.core.domain.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientFile
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result

interface ContentManager {
    suspend fun getPatients(doctorId: String): Result<List<Patient>, Error.GetContentErrors>
    suspend fun getPatientFiles(patient: Patient, graphsOnly : Boolean = false): Result<List<PatientFile>, Error.GetContentErrors>
}