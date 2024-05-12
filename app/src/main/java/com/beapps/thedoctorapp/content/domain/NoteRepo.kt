package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface NoteRepo {
    fun getPatientNotes(
        patient: Patient,
    ): Flow<List<PatientNote>>

    suspend fun insertNote (note: PatientNote)
    suspend fun updateNote (note: PatientNote)
    suspend fun deleteNote (note: PatientNote)
}