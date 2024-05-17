package com.beapps.thedoctorapp.content.domain

import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result

interface PatientNotesManager {
    suspend fun getPatientNotes(
        patientId: String,
        doctorId: String
    ): Result<List<PatientNote> , Error.ManageNotesErrors>

    suspend fun getNoteContent(note: PatientNote): Result<String, Error.ManageNotesErrors>
    suspend fun insertNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors>
    suspend fun updateNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors>
    suspend fun deleteNote(note: PatientNote): Result<List<PatientNote>, Error.ManageNotesErrors>
}