package com.beapps.thedoctorapp.content.data.local.db.repo

import com.beapps.thedoctorapp.content.data.local.db.NoteDb
import com.beapps.thedoctorapp.content.data.local.db.toNoteEntity
import com.beapps.thedoctorapp.content.data.local.db.toPatientNote
import com.beapps.thedoctorapp.content.domain.NoteRepo
import com.beapps.thedoctorapp.content.domain.models.Patient
import com.beapps.thedoctorapp.content.domain.models.PatientNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepoImpl(private val noteDb: NoteDb) : NoteRepo {
    override fun getPatientNotes(
        patient: Patient,
    ): Flow<List<PatientNote>> {

        val notes = noteDb.noteDao.getAllNotes(patient.assignedDoctorId, patient.id).map {
            it.map { noteEntity -> noteEntity.toPatientNote() }
        }

        return notes
    }

    override suspend fun insertNote(note: PatientNote) {
        noteDb.noteDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: PatientNote) {
        noteDb.noteDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: PatientNote) {
        noteDb.noteDao.deleteNote(note.toNoteEntity())
    }
}