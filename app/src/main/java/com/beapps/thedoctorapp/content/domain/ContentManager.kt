package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientContent
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result

interface ContentManager {
    suspend fun displayPatients(doctorId : String) : Result<List<Patient> , Error.GetContentErrors>
    suspend fun getPatientContent(doctorId : String , patientFolderName: String) : Result<List<PatientContent> , Error.GetContentErrors>
}