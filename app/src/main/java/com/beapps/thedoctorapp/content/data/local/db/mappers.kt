package com.beapps.thedoctorapp.content.data.local.db

import com.beapps.thedoctorapp.content.data.local.db.NoteEntity
import com.beapps.thedoctorapp.content.domain.models.PatientNote

fun NoteEntity.toPatientNote() =
    PatientNote(
        id, note, createdAt, byDoctorId, toPatientId
    )


fun PatientNote.toNoteEntity() =
    NoteEntity(
        id = id , note = note, createdAt = createdAt, byDoctorId = byDoctorId, toPatientId = toPatientId
    )