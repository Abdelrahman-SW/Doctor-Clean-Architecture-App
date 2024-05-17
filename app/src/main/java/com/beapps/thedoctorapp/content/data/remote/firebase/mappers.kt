package com.beapps.thedoctorapp.content.data.remote.firebase

import com.beapps.thedoctorapp.content.data.remote.firebase.dto.PatientNoteDto
import com.beapps.thedoctorapp.content.domain.models.PatientNote

fun PatientNoteDto.toPatientNote() =
    PatientNote(
        id.dropLast(4), note, createdAt.toLong() , byDoctorId , toPatientId
    )


fun PatientNote.toPatientNoteDto() =
    PatientNoteDto(
        id = "$id.txt",
        note = note,
        createdAt = createdAt.toString(),
        toPatientId = toPatientId,
        byDoctorId = byDoctorId
    )