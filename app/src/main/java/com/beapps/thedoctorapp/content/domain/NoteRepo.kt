package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface NoteRepo {
    fun getPatientNotes(
        patient: Patient,
        doctorId: String
    ): Flow<List<PatientNote>>
}