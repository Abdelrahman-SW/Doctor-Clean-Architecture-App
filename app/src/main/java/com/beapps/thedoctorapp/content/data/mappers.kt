package com.beapps.thedoctorapp.content.data

import com.beapps.thedoctorapp.content.data.db.NoteEntity
import com.beapps.thedoctorapp.content.domain.models.PatientNote

fun NoteEntity.toPatientNote() =
    PatientNote(
        id, note, createdAt, byDoctorId, toPatientId
    )


fun PatientNote.toNoteEntity() =
    NoteEntity(
        note = note, createdAt = createdAt, byDoctorId = byDoctorId, toPatientId = toPatientId
    )