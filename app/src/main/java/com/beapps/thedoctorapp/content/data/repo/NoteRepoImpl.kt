package com.beapps.thedoctorapp.content.data.repo

import com.beapps.thedoctorapp.content.data.db.NoteDb
import com.beapps.thedoctorapp.content.data.toPatientNote
import com.beapps.thedoctorapp.content.domain.NoteRepo
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import com.beapps.thedoctorapp.core.domain.Error
import com.beapps.thedoctorapp.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl(private val noteDb: NoteDb) : NoteRepo {
    override fun getPatientNotes(
        patient: Patient,
        doctorId: String
    ): Flow<List<PatientNote>> {

        val notes = noteDb.noteDao.getAllNotes(doctorId, patient.id).map {
            it.map { noteEntity -> noteEntity.toPatientNote() }
        }

        return notes
    }
}